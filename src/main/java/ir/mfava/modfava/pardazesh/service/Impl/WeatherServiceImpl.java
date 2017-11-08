package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.DataFile;
import ir.mfava.modfava.pardazesh.model.Phenomena;
import ir.mfava.modfava.pardazesh.model.Weather;
import ir.mfava.modfava.pardazesh.model.WeatherStation;
import ir.mfava.modfava.pardazesh.repository.WeatherRepository;
import ir.mfava.modfava.pardazesh.service.DataFileService;
import ir.mfava.modfava.pardazesh.service.PhenomenaService;
import ir.mfava.modfava.pardazesh.service.WeatherService;
import ir.mfava.modfava.pardazesh.service.WeatherStationService;
import ir.mfava.modfava.pardazesh.util.metar.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static Boolean PROCESS_IN_PROGRESS = false;

    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private WeatherStationService weatherStationService;
    @Autowired
    private PhenomenaService phenomenaService;
    @Autowired
    private DataFileService dataFileService;

    @Override
    public boolean exists(Weather entity) {
        return weatherRepository.exists(Example.of(entity));
    }

    @Override
    public List<Weather> getAll() {
        return weatherRepository.findAll();
    }

    @Override
    public Weather getById(Long id) {
        return weatherRepository.getOne(id);
    }

    @Override
    public Weather save(Weather entity) {
        return weatherRepository.save(entity);
    }

    @Override
    public void remove(Weather entity) {
        weatherRepository.delete(entity);
    }


    @Override
    public List<Weather> getCurrentWeather() {
        return weatherRepository.getCurrentWeather();
    }


    @Override
    public synchronized Boolean process() {
        if (PROCESS_IN_PROGRESS) {
            return false;
        } else {
            PROCESS_IN_PROGRESS = true;
            try {
                File baseDirectory = new File("/d/weather_base_directory/");
                if (!baseDirectory.exists()) baseDirectory.mkdirs();
                File baseProcessedDirectory = new File("/d/weather_base_directory/processed/");
                if (!baseProcessedDirectory.exists()) baseProcessedDirectory.mkdirs();
                File metarDirectory = new File("/d/weather_base_directory/metar/");
                if (!metarDirectory.exists()) metarDirectory.mkdirs();
                if (metarDirectory.listFiles() != null) {
                    List<File> metarFiles = Arrays.asList(metarDirectory.listFiles());
                    for (File metarFile : metarFiles) {
                        DataFile dataFile = new DataFile();
                        dataFile.setFileName(metarFile.getName());
                        dataFile.setFileType("METAR");
                        dataFile.setProcessStartDate(new Date());
                        processFile("/d/weather_base_directory/metar/" + metarFile.getName());
                        dataFile.setProcessEndDate(new Date());
                        File metarProcessedDirectory = new File("/d/weather_base_directory/processed/metar/");
                        if (!metarProcessedDirectory.exists()) metarProcessedDirectory.mkdirs();
                        File newFile = new File("/d/weather_base_directory/processed/metar/" + metarFile.getName());
                        FileCopyUtils.copy(metarFile,newFile);
                        metarFile.delete();
                        dataFileService.save(dataFile);
                    }
                }
                File taforDirectory = new File("/d/weather_base_directory/tafor/");
                if (!taforDirectory.exists()) taforDirectory.mkdirs();
                if (taforDirectory.listFiles() != null) {
                    List<File> metarFiles = Arrays.asList(taforDirectory.listFiles());
                    for (File metarFile : metarFiles) {
                        DataFile dataFile = new DataFile();
                        dataFile.setFileName(metarFile.getName());
                        dataFile.setFileType("TAFOR");
                        dataFile.setProcessStartDate(new Date());
                        processFile("/d/weather_base_directory/tafor/"+metarFile.getName());
                        dataFile.setProcessEndDate(new Date());

                        File metarProcessedDirectory = new File("/d/weather_base_directory/processed/tafor/");
                        if (!metarProcessedDirectory.exists()) metarProcessedDirectory.mkdirs();
                        File newFile = new File("/d/weather_base_directory/processed/tafor/" + metarFile.getName());
                        FileCopyUtils.copy(metarFile,newFile);
                        metarFile.delete();

                        dataFileService.save(dataFile);
                    }
                }
                File speciDirectory = new File("/d/weather_base_directory/speci/");
                if (!speciDirectory.exists()) speciDirectory.mkdirs();
                if (speciDirectory.listFiles() != null) {
                    List<File> metarFiles = Arrays.asList(speciDirectory.listFiles());
                    for (File metarFile : metarFiles) {
                        DataFile dataFile = new DataFile();
                        dataFile.setFileName(metarFile.getName());
                        dataFile.setFileType("SPECI");
                        dataFile.setProcessStartDate(new Date());
                        processFile("/d/weather_base_directory/speci/"+metarFile.getName());
                        dataFile.setProcessEndDate(new Date());

                        File metarProcessedDirectory = new File("/d/weather_base_directory/processed/speci/");
                        if (!metarProcessedDirectory.exists()) metarProcessedDirectory.mkdirs();
                        File newFile = new File("/d/weather_base_directory/processed/speci/" + metarFile.getName());
                        FileCopyUtils.copy(metarFile,newFile);
                        metarFile.delete();

                        dataFileService.save(dataFile);
                    }
                }
                PROCESS_IN_PROGRESS = false;
                return true;
            } catch (Exception ex) {
                System.out.println("error in process ");
                ex.printStackTrace();
                PROCESS_IN_PROGRESS = false;
                return false;
            }
        }
    }

    private void processFile(String fileName) {
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {

                if (sCurrentLine.startsWith("METAR") || sCurrentLine.startsWith("SPECI") || sCurrentLine.startsWith("TAF")) {
                    String record = sCurrentLine;
                    while (((sCurrentLine = br.readLine()) != null) && !record.endsWith("=")) {
                        record = record + " " + sCurrentLine;
                    }
                    Weather weather = new Weather();
                    weather.setOriginalContent(record);
                    Weather.ReportType type = null;
                    if (record.startsWith("METAR")) {
                        type = Weather.ReportType.METAR;
                    }
                    if (record.startsWith("TAF")) {
                        type = Weather.ReportType.TAFOR;
                    }
                    if (record.startsWith("SPECI")) {
                        type = Weather.ReportType.SPECI;
                    }
                    record = record.replaceAll("METAR", "").replaceAll("TAF", "").replaceAll("SPECI", "").trim();
                    record = record.replace("=", " =");
                    Metar metar = new Metar();
                    try {
                        if (type.equals(Weather.ReportType.METAR) || type.equals(Weather.ReportType.SPECI)) {
                            metar = MetarParser.parseReport(record);
                        }
                        if (type.equals(Weather.ReportType.TAFOR)) {
                            metar = MetarParser.parseTAFReport(record);
                        }
                        WeatherStation weatherStation = weatherStationService.getByStationId(metar.getStationID());
                        if (weatherStation == null) {
                            return;
                        }
                        weather.setReportType(type);
                        weather.setReportDate(metar.getDate());
                        weather.setWeatherStation(weatherStation);
                        weather.setFileName(fileName);
                        weather.setWindSpeed(metar.getWindSpeedInKnots());
                        weather.setWindDirection(metar.getWindDirection());
                        weather.setVisibility(String.valueOf(metar.getVisibilityInMeters()));
                        if(!metar.getRunwayVisualRanges().isEmpty()){
                            weather.setRvr(metar.getRunwayVisualRange(0).getNaturalLanguageString());
                        }
                        SkyCondition skyCondition = getHighestPriority(metar.getSkyConditions());
                        if(skyCondition != null) {
                            weather.setCloud(skyCondition.getContraction());
                        }

                        weather.setDewPoint(metar.getDewPointInCelsius());
                        weather.setTemperature(metar.getTemperatureInCelsius());
                        weather.setTemperaturePrecise(metar.getTemperaturePreciseInCelsius());
                        weather.setPressure(metar.getPressure());
                        weather.setCreateDate(new Date());
                        weather.setPhenomena(getHighestPriorityPhenomena(metar.getWeatherConditions()));
                        weather.setFileName(fileName);
                        save(weather);
                    } catch (Exception ex) {
                        System.out.println("error processing record :");
                        System.out.println("file name:" + fileName);
                        ex.printStackTrace();
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private SkyCondition getHighestPriority(List<SkyCondition> skyConditions) {
        for (SkyCondition skyCondition : skyConditions) {
            if (skyCondition.getContraction().equals(MetarConstants.METAR_OVERCAST)) {
                return skyCondition;
            }
        }
        for (SkyCondition skyCondition : skyConditions) {
            if (skyCondition.getContraction().equals(MetarConstants.METAR_BROKEN)) {
                return skyCondition;
            }
        }
        for (SkyCondition skyCondition : skyConditions) {
            if (skyCondition.getContraction().equals(MetarConstants.METAR_SCATTERED)) {
                return skyCondition;
            }
        }
        for (SkyCondition skyCondition : skyConditions) {
            if (skyCondition.getContraction().equals(MetarConstants.METAR_FEW)) {
                return skyCondition;
            }
        }
        return null;
    }

    private Phenomena getHighestPriorityPhenomena(List<WeatherCondition> weatherConditions) {
        List<Phenomena> phenomenas = new ArrayList<>();
        for (WeatherCondition weatherCondition : weatherConditions) {
            phenomenas.add(phenomenaService.getByAbbreviation(weatherCondition.getPhenomena()));
        }
        if(phenomenas.isEmpty()){
            return null;
        }
        Comparator<Phenomena> phenomenaComparator = new Comparator<Phenomena>() {
            @Override
            public int compare(Phenomena o1, Phenomena o2) {
                return o2.getPriority().compareTo(o1.getPriority());
            }
        };
        Collections.sort(phenomenas, phenomenaComparator);

        return phenomenas.get(0);
    }
}

