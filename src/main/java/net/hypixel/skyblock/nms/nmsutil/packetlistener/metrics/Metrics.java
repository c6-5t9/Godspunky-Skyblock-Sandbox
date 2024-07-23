package net.hypixel.skyblock.nms.nmsutil.packetlistener.metrics;

import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.nio.charset.StandardCharsets;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.util.logging.Level;
import java.lang.reflect.InvocationTargetException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.Bukkit;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

public class Metrics
{
    public static final int B_STATS_VERSION = 1;
    private static final String URL = "https://bStats.org/submitData";
    private static boolean logFailedRequests;
    private static String serverUUID;
    private final JavaPlugin plugin;
    private final List<CustomChart> charts;
    
    public Metrics(final JavaPlugin plugin) {
        this.charts = (List<CustomChart>)new ArrayList();
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null!");
        }
        this.plugin = plugin;
        final File bStatsFolder = new File(plugin.getDataFolder().getParentFile(), "bStats");
        final File configFile = new File(bStatsFolder, "config.yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (!config.isSet("serverUuid")) {
            config.addDefault("enabled", (Object)true);
            config.addDefault("serverUuid", (Object)UUID.randomUUID().toString());
            config.addDefault("logFailedRequests", (Object)false);
            config.options().header("bStats collects some data for plugin authors like how many servers are using their plugins.\nTo honor their work, you should not disable it.\nThis has nearly no effect on the server performance!\nCheck out https://bStats.org/ to learn more :)").copyDefaults(true);
            try {
                config.save(configFile);
            }
            catch (final IOException ex3) {}
        }
        Metrics.serverUUID = config.getString("serverUuid");
        Metrics.logFailedRequests = config.getBoolean("logFailedRequests", false);
        if (config.getBoolean("enabled", true)) {
            boolean found = false;
            for (final Class<?> service : Bukkit.getServicesManager().getKnownServices()) {
                try {
                    service.getField("B_STATS_VERSION");
                    found = true;
                }
                catch (final NoSuchFieldException ex2) {
                    continue;
                }
                break;
            }
            Bukkit.getServicesManager().register((Class)Metrics.class, (Object)this, (Plugin)plugin, ServicePriority.Normal);
            if (!found) {
                this.startSubmitting();
            }
        }
    }
    
    public void addCustomChart(final CustomChart chart) {
        if (chart == null) {
            throw new IllegalArgumentException("Chart cannot be null!");
        }
        this.charts.add((Object)chart);
    }
    
    private void startSubmitting() {
        final Timer timer = new Timer(true);
        timer.scheduleAtFixedRate((TimerTask)new TimerTask() {
            public void run() {
                if (!Metrics.this.plugin.isEnabled()) {
                    timer.cancel();
                    return;
                }
                Bukkit.getScheduler().runTask((Plugin)Metrics.this.plugin, (Runnable)new Runnable() {
                    public void run() {
                        Metrics.this.submitData();
                    }
                });
            }
        }, 300000L, 1800000L);
    }
    
    public JSONObject getPluginData() {
        final JSONObject data = new JSONObject();
        final String pluginName = this.plugin.getDescription().getName();
        final String pluginVersion = this.plugin.getDescription().getVersion();
        data.put((Object)"pluginName", (Object)pluginName);
        data.put((Object)"pluginVersion", (Object)pluginVersion);
        final JSONArray customCharts = new JSONArray();
        for (final CustomChart customChart : this.charts) {
            final JSONObject chart = customChart.getRequestJsonObject();
            if (chart == null) {
                continue;
            }
            customCharts.add((Object)chart);
        }
        data.put((Object)"customCharts", (Object)customCharts);
        return data;
    }
    
    private JSONObject getServerData() {
        final int playerAmount = Bukkit.getOnlinePlayers().size();
        final int onlineMode = Bukkit.getOnlineMode() ? 1 : 0;
        String bukkitVersion = Bukkit.getVersion();
        bukkitVersion = bukkitVersion.substring(bukkitVersion.indexOf("MC: ") + 4, bukkitVersion.length() - 1);
        final String javaVersion = System.getProperty("java.version");
        final String osName = System.getProperty("os.name");
        final String osArch = System.getProperty("os.arch");
        final String osVersion = System.getProperty("os.version");
        final int coreCount = Runtime.getRuntime().availableProcessors();
        final JSONObject data = new JSONObject();
        data.put((Object)"serverUUID", (Object)Metrics.serverUUID);
        data.put((Object)"playerAmount", (Object)playerAmount);
        data.put((Object)"onlineMode", (Object)onlineMode);
        data.put((Object)"bukkitVersion", (Object)bukkitVersion);
        data.put((Object)"javaVersion", (Object)javaVersion);
        data.put((Object)"osName", (Object)osName);
        data.put((Object)"osArch", (Object)osArch);
        data.put((Object)"osVersion", (Object)osVersion);
        data.put((Object)"coreCount", (Object)coreCount);
        return data;
    }
    
    private void submitData() {
        final JSONObject data = this.getServerData();
        final JSONArray pluginData = new JSONArray();
        for (final Class<?> service : Bukkit.getServicesManager().getKnownServices()) {
            try {
                service.getField("B_STATS_VERSION");
            }
            catch (final NoSuchFieldException ignored) {
                continue;
            }
            try {
                pluginData.add(service.getMethod("getPluginData", (Class<?>[])new Class[0]).invoke(Bukkit.getServicesManager().load((Class)service), new Object[0]));
            }
            catch (final NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {}
        }
        data.put((Object)"plugins", (Object)pluginData);
        new Thread((Runnable)new Runnable() {
            public void run() {
                try {
                    sendData(data);
                }
                catch (final Exception e) {
                    if (Metrics.logFailedRequests) {
                        Metrics.this.plugin.getLogger().log(Level.WARNING, "Could not submit plugin stats of " + Metrics.this.plugin.getName(), (Throwable)e);
                    }
                }
            }
        }).start();
    }
    
    private static void sendData(final JSONObject data) throws Exception {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        if (Bukkit.isPrimaryThread()) {
            throw new IllegalAccessException("This method must not be called from the main thread!");
        }
        final HttpsURLConnection connection = (HttpsURLConnection)new URL("https://bStats.org/submitData").openConnection();
        final byte[] compressedData = compress(data.toString());
        connection.setRequestMethod("POST");
        connection.addRequestProperty("Accept", "application/json");
        connection.addRequestProperty("Connection", "close");
        connection.addRequestProperty("Content-Encoding", "gzip");
        connection.addRequestProperty("Content-Length", String.valueOf(compressedData.length));
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "MC-Server/1");
        connection.setDoOutput(true);
        final DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(compressedData);
        outputStream.flush();
        outputStream.close();
        connection.getInputStream().close();
    }
    
    private static byte[] compress(final String str) throws IOException {
        if (str == null) {
            return null;
        }
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final GZIPOutputStream gzip = new GZIPOutputStream((OutputStream)outputStream);
        gzip.write(str.getBytes(StandardCharsets.UTF_8));
        gzip.close();
        return outputStream.toByteArray();
    }
    
    public abstract static class CustomChart
    {
        protected final String chartId;
        
        public CustomChart(final String chartId) {
            if (chartId == null || chartId.isEmpty()) {
                throw new IllegalArgumentException("ChartId cannot be null or empty!");
            }
            this.chartId = chartId;
        }
        
        protected JSONObject getRequestJsonObject() {
            final JSONObject chart = new JSONObject();
            chart.put((Object)"chartId", (Object)this.chartId);
            try {
                final JSONObject data = this.getChartData();
                if (data == null) {
                    return null;
                }
                chart.put((Object)"data", (Object)data);
            }
            catch (final Throwable t) {
                if (Metrics.logFailedRequests) {
                    Bukkit.getLogger().log(Level.WARNING, "Failed to get data for custom chart with id " + this.chartId, t);
                }
                return null;
            }
            return chart;
        }
        
        protected abstract JSONObject getChartData();
    }
    
    public abstract static class SimplePie extends CustomChart
    {
        public SimplePie(final String chartId) {
            super(chartId);
        }
        
        public abstract String getValue();
        
        @Override
        protected JSONObject getChartData() {
            final JSONObject data = new JSONObject();
            final String value = this.getValue();
            if (value == null || value.isEmpty()) {
                return null;
            }
            data.put((Object)"value", (Object)value);
            return data;
        }
    }
    
    public abstract static class AdvancedPie extends CustomChart
    {
        public AdvancedPie(final String chartId) {
            super(chartId);
        }
        
        public abstract HashMap<String, Integer> getValues(final HashMap<String, Integer> p0);
        
        @Override
        protected JSONObject getChartData() {
            final JSONObject data = new JSONObject();
            final JSONObject values = new JSONObject();
            final HashMap<String, Integer> map = this.getValues((HashMap<String, Integer>)new HashMap());
            if (map == null || map.isEmpty()) {
                return null;
            }
            boolean allSkipped = true;
            for (final Map.Entry<String, Integer> entry : map.entrySet()) {
                if ((int)entry.getValue() == 0) {
                    continue;
                }
                allSkipped = false;
                values.put(entry.getKey(), entry.getValue());
            }
            if (allSkipped) {
                return null;
            }
            data.put((Object)"values", (Object)values);
            return data;
        }
    }
    
    public abstract static class SingleLineChart extends CustomChart
    {
        public SingleLineChart(final String chartId) {
            super(chartId);
        }
        
        public abstract int getValue();
        
        @Override
        protected JSONObject getChartData() {
            final JSONObject data = new JSONObject();
            final int value = this.getValue();
            if (value == 0) {
                return null;
            }
            data.put((Object)"value", (Object)value);
            return data;
        }
    }
    
    public abstract static class MultiLineChart extends CustomChart
    {
        public MultiLineChart(final String chartId) {
            super(chartId);
        }
        
        public abstract HashMap<String, Integer> getValues(final HashMap<String, Integer> p0);
        
        @Override
        protected JSONObject getChartData() {
            final JSONObject data = new JSONObject();
            final JSONObject values = new JSONObject();
            final HashMap<String, Integer> map = this.getValues((HashMap<String, Integer>)new HashMap());
            if (map == null || map.isEmpty()) {
                return null;
            }
            boolean allSkipped = true;
            for (final Map.Entry<String, Integer> entry : map.entrySet()) {
                if ((int)entry.getValue() == 0) {
                    continue;
                }
                allSkipped = false;
                values.put(entry.getKey(), entry.getValue());
            }
            if (allSkipped) {
                return null;
            }
            data.put((Object)"values", (Object)values);
            return data;
        }
    }
    
    public abstract static class SimpleBarChart extends CustomChart
    {
        public SimpleBarChart(final String chartId) {
            super(chartId);
        }
        
        public abstract HashMap<String, Integer> getValues(final HashMap<String, Integer> p0);
        
        @Override
        protected JSONObject getChartData() {
            final JSONObject data = new JSONObject();
            final JSONObject values = new JSONObject();
            final HashMap<String, Integer> map = this.getValues((HashMap<String, Integer>)new HashMap());
            if (map == null || map.isEmpty()) {
                return null;
            }
            for (final Map.Entry<String, Integer> entry : map.entrySet()) {
                final JSONArray categoryValues = new JSONArray();
                categoryValues.add(entry.getValue());
                values.put(entry.getKey(), (Object)categoryValues);
            }
            data.put((Object)"values", (Object)values);
            return data;
        }
    }
    
    public abstract static class AdvancedBarChart extends CustomChart
    {
        public AdvancedBarChart(final String chartId) {
            super(chartId);
        }
        
        public abstract HashMap<String, int[]> getValues(final HashMap<String, int[]> p0);
        
        @Override
        protected JSONObject getChartData() {
            final JSONObject data = new JSONObject();
            final JSONObject values = new JSONObject();
            final HashMap<String, int[]> map = this.getValues((HashMap<String, int[]>)new HashMap());
            if (map == null || map.isEmpty()) {
                return null;
            }
            boolean allSkipped = true;
            for (final Map.Entry<String, int[]> entry : map.entrySet()) {
                if (((int[])entry.getValue()).length == 0) {
                    continue;
                }
                allSkipped = false;
                final JSONArray categoryValues = new JSONArray();
                for (final int categoryValue : (int[])entry.getValue()) {
                    categoryValues.add((Object)categoryValue);
                }
                values.put(entry.getKey(), (Object)categoryValues);
            }
            if (allSkipped) {
                return null;
            }
            data.put((Object)"values", (Object)values);
            return data;
        }
    }
    
    public abstract static class SimpleMapChart extends CustomChart
    {
        public SimpleMapChart(final String chartId) {
            super(chartId);
        }
        
        public abstract Country getValue();
        
        @Override
        protected JSONObject getChartData() {
            final JSONObject data = new JSONObject();
            final Country value = this.getValue();
            if (value == null) {
                return null;
            }
            data.put((Object)"value", (Object)value.getCountryIsoTag());
            return data;
        }
    }
    
    public abstract static class AdvancedMapChart extends CustomChart
    {
        public AdvancedMapChart(final String chartId) {
            super(chartId);
        }
        
        public abstract HashMap<Country, Integer> getValues(final HashMap<Country, Integer> p0);
        
        @Override
        protected JSONObject getChartData() {
            final JSONObject data = new JSONObject();
            final JSONObject values = new JSONObject();
            final HashMap<Country, Integer> map = this.getValues((HashMap<Country, Integer>)new HashMap());
            if (map == null || map.isEmpty()) {
                return null;
            }
            boolean allSkipped = true;
            for (final Map.Entry<Country, Integer> entry : map.entrySet()) {
                if ((int)entry.getValue() == 0) {
                    continue;
                }
                allSkipped = false;
                values.put((Object)((Country)entry.getKey()).getCountryIsoTag(), entry.getValue());
            }
            if (allSkipped) {
                return null;
            }
            data.put((Object)"values", (Object)values);
            return data;
        }
    }
    
    public enum Country
    {
        AUTO_DETECT("AUTO", "Auto Detected"), 
        ANDORRA("AD", "Andorra"), 
        UNITED_ARAB_EMIRATES("AE", "United Arab Emirates"), 
        AFGHANISTAN("AF", "Afghanistan"), 
        ANTIGUA_AND_BARBUDA("AG", "Antigua and Barbuda"), 
        ANGUILLA("AI", "Anguilla"), 
        ALBANIA("AL", "Albania"), 
        ARMENIA("AM", "Armenia"), 
        NETHERLANDS_ANTILLES("AN", "Netherlands Antilles"), 
        ANGOLA("AO", "Angola"), 
        ANTARCTICA("AQ", "Antarctica"), 
        ARGENTINA("AR", "Argentina"), 
        AMERICAN_SAMOA("AS", "American Samoa"), 
        AUSTRIA("AT", "Austria"), 
        AUSTRALIA("AU", "Australia"), 
        ARUBA("AW", "Aruba"), 
        \u00c5LAND_ISLANDS("AX", "\u00c5land Islands"), 
        AZERBAIJAN("AZ", "Azerbaijan"), 
        BOSNIA_AND_HERZEGOVINA("BA", "Bosnia and Herzegovina"), 
        BARBADOS("BB", "Barbados"), 
        BANGLADESH("BD", "Bangladesh"), 
        BELGIUM("BE", "Belgium"), 
        BURKINA_FASO("BF", "Burkina Faso"), 
        BULGARIA("BG", "Bulgaria"), 
        BAHRAIN("BH", "Bahrain"), 
        BURUNDI("BI", "Burundi"), 
        BENIN("BJ", "Benin"), 
        SAINT_BARTH\u00c9LEMY("BL", "Saint Barth\u00e9lemy"), 
        BERMUDA("BM", "Bermuda"), 
        BRUNEI("BN", "Brunei"), 
        BOLIVIA("BO", "Bolivia"), 
        BONAIRE_SINT_EUSTATIUS_AND_SABA("BQ", "Bonaire, Sint Eustatius and Saba"), 
        BRAZIL("BR", "Brazil"), 
        BAHAMAS("BS", "Bahamas"), 
        BHUTAN("BT", "Bhutan"), 
        BOUVET_ISLAND("BV", "Bouvet Island"), 
        BOTSWANA("BW", "Botswana"), 
        BELARUS("BY", "Belarus"), 
        BELIZE("BZ", "Belize"), 
        CANADA("CA", "Canada"), 
        COCOS_ISLANDS("CC", "Cocos Islands"), 
        THE_DEMOCRATIC_REPUBLIC_OF_CONGO("CD", "The Democratic Republic Of Congo"), 
        CENTRAL_AFRICAN_REPUBLIC("CF", "Central African Republic"), 
        CONGO("CG", "Congo"), 
        SWITZERLAND("CH", "Switzerland"), 
        C\u00d4TE_D_IVOIRE("CI", "C\u00f4te d'Ivoire"), 
        COOK_ISLANDS("CK", "Cook Islands"), 
        CHILE("CL", "Chile"), 
        CAMEROON("CM", "Cameroon"), 
        CHINA("CN", "China"), 
        COLOMBIA("CO", "Colombia"), 
        COSTA_RICA("CR", "Costa Rica"), 
        CUBA("CU", "Cuba"), 
        CAPE_VERDE("CV", "Cape Verde"), 
        CURA\u00c7AO("CW", "Cura\u00e7ao"), 
        CHRISTMAS_ISLAND("CX", "Christmas Island"), 
        CYPRUS("CY", "Cyprus"), 
        CZECH_REPUBLIC("CZ", "Czech Republic"), 
        GERMANY("DE", "Germany"), 
        DJIBOUTI("DJ", "Djibouti"), 
        DENMARK("DK", "Denmark"), 
        DOMINICA("DM", "Dominica"), 
        DOMINICAN_REPUBLIC("DO", "Dominican Republic"), 
        ALGERIA("DZ", "Algeria"), 
        ECUADOR("EC", "Ecuador"), 
        ESTONIA("EE", "Estonia"), 
        EGYPT("EG", "Egypt"), 
        WESTERN_SAHARA("EH", "Western Sahara"), 
        ERITREA("ER", "Eritrea"), 
        SPAIN("ES", "Spain"), 
        ETHIOPIA("ET", "Ethiopia"), 
        FINLAND("FI", "Finland"), 
        FIJI("FJ", "Fiji"), 
        FALKLAND_ISLANDS("FK", "Falkland Islands"), 
        MICRONESIA("FM", "Micronesia"), 
        FAROE_ISLANDS("FO", "Faroe Islands"), 
        FRANCE("FR", "France"), 
        GABON("GA", "Gabon"), 
        UNITED_KINGDOM("GB", "United Kingdom"), 
        GRENADA("GD", "Grenada"), 
        GEORGIA("GE", "Georgia"), 
        FRENCH_GUIANA("GF", "French Guiana"), 
        GUERNSEY("GG", "Guernsey"), 
        GHANA("GH", "Ghana"), 
        GIBRALTAR("GI", "Gibraltar"), 
        GREENLAND("GL", "Greenland"), 
        GAMBIA("GM", "Gambia"), 
        GUINEA("GN", "Guinea"), 
        GUADELOUPE("GP", "Guadeloupe"), 
        EQUATORIAL_GUINEA("GQ", "Equatorial Guinea"), 
        GREECE("GR", "Greece"), 
        SOUTH_GEORGIA_AND_THE_SOUTH_SANDWICH_ISLANDS("GS", "South Georgia And The South Sandwich Islands"), 
        GUATEMALA("GT", "Guatemala"), 
        GUAM("GU", "Guam"), 
        GUINEA_BISSAU("GW", "Guinea-Bissau"), 
        GUYANA("GY", "Guyana"), 
        HONG_KONG("HK", "Hong Kong"), 
        HEARD_ISLAND_AND_MCDONALD_ISLANDS("HM", "Heard Island And McDonald Islands"), 
        HONDURAS("HN", "Honduras"), 
        CROATIA("HR", "Croatia"), 
        HAITI("HT", "Haiti"), 
        HUNGARY("HU", "Hungary"), 
        INDONESIA("ID", "Indonesia"), 
        IRELAND("IE", "Ireland"), 
        ISRAEL("IL", "Israel"), 
        ISLE_OF_MAN("IM", "Isle Of Man"), 
        INDIA("IN", "India"), 
        BRITISH_INDIAN_OCEAN_TERRITORY("IO", "British Indian Ocean Territory"), 
        IRAQ("IQ", "Iraq"), 
        IRAN("IR", "Iran"), 
        ICELAND("IS", "Iceland"), 
        ITALY("IT", "Italy"), 
        JERSEY("JE", "Jersey"), 
        JAMAICA("JM", "Jamaica"), 
        JORDAN("JO", "Jordan"), 
        JAPAN("JP", "Japan"), 
        KENYA("KE", "Kenya"), 
        KYRGYZSTAN("KG", "Kyrgyzstan"), 
        CAMBODIA("KH", "Cambodia"), 
        KIRIBATI("KI", "Kiribati"), 
        COMOROS("KM", "Comoros"), 
        SAINT_KITTS_AND_NEVIS("KN", "Saint Kitts And Nevis"), 
        NORTH_KOREA("KP", "North Korea"), 
        SOUTH_KOREA("KR", "South Korea"), 
        KUWAIT("KW", "Kuwait"), 
        CAYMAN_ISLANDS("KY", "Cayman Islands"), 
        KAZAKHSTAN("KZ", "Kazakhstan"), 
        LAOS("LA", "Laos"), 
        LEBANON("LB", "Lebanon"), 
        SAINT_LUCIA("LC", "Saint Lucia"), 
        LIECHTENSTEIN("LI", "Liechtenstein"), 
        SRI_LANKA("LK", "Sri Lanka"), 
        LIBERIA("LR", "Liberia"), 
        LESOTHO("LS", "Lesotho"), 
        LITHUANIA("LT", "Lithuania"), 
        LUXEMBOURG("LU", "Luxembourg"), 
        LATVIA("LV", "Latvia"), 
        LIBYA("LY", "Libya"), 
        MOROCCO("MA", "Morocco"), 
        MONACO("MC", "Monaco"), 
        MOLDOVA("MD", "Moldova"), 
        MONTENEGRO("ME", "Montenegro"), 
        SAINT_MARTIN("MF", "Saint Martin"), 
        MADAGASCAR("MG", "Madagascar"), 
        MARSHALL_ISLANDS("MH", "Marshall Islands"), 
        MACEDONIA("MK", "Macedonia"), 
        MALI("ML", "Mali"), 
        MYANMAR("MM", "Myanmar"), 
        MONGOLIA("MN", "Mongolia"), 
        MACAO("MO", "Macao"), 
        NORTHERN_MARIANA_ISLANDS("MP", "Northern Mariana Islands"), 
        MARTINIQUE("MQ", "Martinique"), 
        MAURITANIA("MR", "Mauritania"), 
        MONTSERRAT("MS", "Montserrat"), 
        MALTA("MT", "Malta"), 
        MAURITIUS("MU", "Mauritius"), 
        MALDIVES("MV", "Maldives"), 
        MALAWI("MW", "Malawi"), 
        MEXICO("MX", "Mexico"), 
        MALAYSIA("MY", "Malaysia"), 
        MOZAMBIQUE("MZ", "Mozambique"), 
        NAMIBIA("NA", "Namibia"), 
        NEW_CALEDONIA("NC", "New Caledonia"), 
        NIGER("NE", "Niger"), 
        NORFOLK_ISLAND("NF", "Norfolk Island"), 
        NIGERIA("NG", "Nigeria"), 
        NICARAGUA("NI", "Nicaragua"), 
        NETHERLANDS("NL", "Netherlands"), 
        NORWAY("NO", "Norway"), 
        NEPAL("NP", "Nepal"), 
        NAURU("NR", "Nauru"), 
        NIUE("NU", "Niue"), 
        NEW_ZEALAND("NZ", "New Zealand"), 
        OMAN("OM", "Oman"), 
        PANAMA("PA", "Panama"), 
        PERU("PE", "Peru"), 
        FRENCH_POLYNESIA("PF", "French Polynesia"), 
        PAPUA_NEW_GUINEA("PG", "Papua New Guinea"), 
        PHILIPPINES("PH", "Philippines"), 
        PAKISTAN("PK", "Pakistan"), 
        POLAND("PL", "Poland"), 
        SAINT_PIERRE_AND_MIQUELON("PM", "Saint Pierre And Miquelon"), 
        PITCAIRN("PN", "Pitcairn"), 
        PUERTO_RICO("PR", "Puerto Rico"), 
        PALESTINE("PS", "Palestine"), 
        PORTUGAL("PT", "Portugal"), 
        PALAU("PW", "Palau"), 
        PARAGUAY("PY", "Paraguay"), 
        QATAR("QA", "Qatar"), 
        REUNION("RE", "Reunion"), 
        ROMANIA("RO", "Romania"), 
        SERBIA("RS", "Serbia"), 
        RUSSIA("RU", "Russia"), 
        RWANDA("RW", "Rwanda"), 
        SAUDI_ARABIA("SA", "Saudi Arabia"), 
        SOLOMON_ISLANDS("SB", "Solomon Islands"), 
        SEYCHELLES("SC", "Seychelles"), 
        SUDAN("SD", "Sudan"), 
        SWEDEN("SE", "Sweden"), 
        SINGAPORE("SG", "Singapore"), 
        SAINT_HELENA("SH", "Saint Helena"), 
        SLOVENIA("SI", "Slovenia"), 
        SVALBARD_AND_JAN_MAYEN("SJ", "Svalbard And Jan Mayen"), 
        SLOVAKIA("SK", "Slovakia"), 
        SIERRA_LEONE("SL", "Sierra Leone"), 
        SAN_MARINO("SM", "San Marino"), 
        SENEGAL("SN", "Senegal"), 
        SOMALIA("SO", "Somalia"), 
        SURINAME("SR", "Suriname"), 
        SOUTH_SUDAN("SS", "South Sudan"), 
        SAO_TOME_AND_PRINCIPE("ST", "Sao Tome And Principe"), 
        EL_SALVADOR("SV", "El Salvador"), 
        SINT_MAARTEN_DUTCH_PART("SX", "Sint Maarten (Dutch part)"), 
        SYRIA("SY", "Syria"), 
        SWAZILAND("SZ", "Swaziland"), 
        TURKS_AND_CAICOS_ISLANDS("TC", "Turks And Caicos Islands"), 
        CHAD("TD", "Chad"), 
        FRENCH_SOUTHERN_TERRITORIES("TF", "French Southern Territories"), 
        TOGO("TG", "Togo"), 
        THAILAND("TH", "Thailand"), 
        TAJIKISTAN("TJ", "Tajikistan"), 
        TOKELAU("TK", "Tokelau"), 
        TIMOR_LESTE("TL", "Timor-Leste"), 
        TURKMENISTAN("TM", "Turkmenistan"), 
        TUNISIA("TN", "Tunisia"), 
        TONGA("TO", "Tonga"), 
        TURKEY("TR", "Turkey"), 
        TRINIDAD_AND_TOBAGO("TT", "Trinidad and Tobago"), 
        TUVALU("TV", "Tuvalu"), 
        TAIWAN("TW", "Taiwan"), 
        TANZANIA("TZ", "Tanzania"), 
        UKRAINE("UA", "Ukraine"), 
        UGANDA("UG", "Uganda"), 
        UNITED_STATES_MINOR_OUTLYING_ISLANDS("UM", "United States Minor Outlying Islands"), 
        UNITED_STATES("US", "United States"), 
        URUGUAY("UY", "Uruguay"), 
        UZBEKISTAN("UZ", "Uzbekistan"), 
        VATICAN("VA", "Vatican"), 
        SAINT_VINCENT_AND_THE_GRENADINES("VC", "Saint Vincent And The Grenadines"), 
        VENEZUELA("VE", "Venezuela"), 
        BRITISH_VIRGIN_ISLANDS("VG", "British Virgin Islands"), 
        U_S__VIRGIN_ISLANDS("VI", "U.S. Virgin Islands"), 
        VIETNAM("VN", "Vietnam"), 
        VANUATU("VU", "Vanuatu"), 
        WALLIS_AND_FUTUNA("WF", "Wallis And Futuna"), 
        SAMOA("WS", "Samoa"), 
        YEMEN("YE", "Yemen"), 
        MAYOTTE("YT", "Mayotte"), 
        SOUTH_AFRICA("ZA", "South Africa"), 
        ZAMBIA("ZM", "Zambia"), 
        ZIMBABWE("ZW", "Zimbabwe");
        
        private final String isoTag;
        private final String name;
        
        private Country(final String isoTag, final String name) {
            this.isoTag = isoTag;
            this.name = name;
        }
        
        public String getCountryName() {
            return this.name;
        }
        
        public String getCountryIsoTag() {
            return this.isoTag;
        }
        
        public static Country byIsoTag(final String isoTag) {
            for (final Country country : values()) {
                if (country.getCountryIsoTag().equals((Object)isoTag)) {
                    return country;
                }
            }
            return null;
        }
        
        public static Country byLocale(final Locale locale) {
            return byIsoTag(locale.getCountry());
        }
    }
}
