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
                    List<File> taforFiles = Arrays.asList(taforDirectory.listFiles());
                    for (File taforFile : taforFiles) {
                        DataFile dataFile = new DataFile();
                        dataFile.setFileName(taforFile.getName());
                        dataFile.setFileType("TAFOR");
                        dataFile.setProcessStartDate(new Date());
                        processFile("/d/weather_base_directory/tafor/"+ taforFile.getName());
                        dataFile.setProcessEndDate(new Date());

                        File taforProcessedDirectory = new File("/d/weather_base_directory/processed/tafor/");
                        if (!taforProcessedDirectory.exists()) taforProcessedDirectory.mkdirs();
                        File newFile = new File("/d/weather_base_directory/processed/tafor/" + taforFile.getName());
                        FileCopyUtils.copy(taforFile,newFile);
                        taforFile.delete();

                        dataFileService.save(dataFile);
                    }
                }
                File speciDirectory = new File("/d/weather_base_directory/speci/");
                if (!speciDirectory.exists()) speciDirectory.mkdirs();
                if (speciDirectory.listFiles() != null) {
                    List<File> speciFiles = Arrays.asList(speciDirectory.listFiles());
                    for (File speciFile : speciFiles) {
                        DataFile dataFile = new DataFile();
                        dataFile.setFileName(speciFile.getName());
                        dataFile.setFileType("SPECI");
                        dataFile.setProcessStartDate(new Date());
                        processFile("/d/weather_base_directory/speci/"+ speciFile.getName());
                        dataFile.setProcessEndDate(new Date());

                        File speciProcessedDirectory = new File("/d/weather_base_directory/processed/speci/");
                        if (!speciProcessedDirectory.exists()) speciProcessedDirectory.mkdirs();
                        File newFile = new File("/d/weather_base_directory/processed/speci/" + speciFile.getName());
                        FileCopyUtils.copy(speciFile,newFile);
                        speciFile.delete();

                        dataFileService.save(dataFile);
                    }
                }

                File satImageProcessedDirectory = new File("/d/weather_base_directory/processed/sat/");
                if (!satImageProcessedDirectory.exists()) satImageProcessedDirectory.mkdirs();
                if (satImageProcessedDirectory.listFiles() != null) {
                    List<File> oldSatImages = Arrays.asList(satImageProcessedDirectory.listFiles());
                    File oldSatImageProcessedDirectory = new File("/d/weather_base_directory/processed/sat/old/");
                    if (!oldSatImageProcessedDirectory.exists()) oldSatImageProcessedDirectory.mkdirs();
                    for (File oldSatImage : oldSatImages) {
                        if(oldSatImage.isFile()){
                        File newFile = new File("/d/weather_base_directory/processed/sat/old/" + oldSatImage.getName());
                        FileCopyUtils.copy(oldSatImage, newFile);
                        }
                    }
                }

                File satImagesDirectory = new File("/d/weather_base_directory/sat/");
                if (!satImagesDirectory.exists()) satImagesDirectory.mkdirs();
                if (satImagesDirectory.listFiles() != null) {
                    List<File> satImages = Arrays.asList(satImagesDirectory.listFiles());
                    for (File satImage : satImages) {
                        if(satImage.isFile()) {
                            File newFile = new File("/d/weather_base_directory/processed/sat/" + satImage.getName());
                            FileCopyUtils.copy(satImage, newFile);
                            satImage.delete();
                        }
                    }
                }

                File dustImageProcessedDirectory = new File("/d/weather_base_directory/processed/dust/");
                if (!dustImageProcessedDirectory.exists()) dustImageProcessedDirectory.mkdirs();
                if (dustImageProcessedDirectory.listFiles() != null) {
                    List<File> oldDustImages = Arrays.asList(dustImageProcessedDirectory.listFiles());
                    File oldSatImageProcessedDirectory = new File("/d/weather_base_directory/processed/sat/old/");
                    if (!oldSatImageProcessedDirectory.exists()) oldSatImageProcessedDirectory.mkdirs();
                    for (File oldDustImage : oldDustImages) {
                        if(oldDustImage.isFile()){
                        File newFile = new File("/d/weather_base_directory/processed/dust/old/" + oldDustImage.getName());
                        FileCopyUtils.copy(oldDustImage, newFile);
                        }
                    }
                }

                File dustImagesDirectory = new File("/d/weather_base_directory/dust/");
                if (!dustImagesDirectory.exists()) dustImagesDirectory.mkdirs();
                if (dustImagesDirectory.listFiles() != null) {
                    List<File> dusttImages = Arrays.asList(dustImagesDirectory.listFiles());
                    for (File dusttImage : dusttImages) {
                        if(dusttImage.isFile()) {
                            File newFile = new File("/d/weather_base_directory/processed/dust/" + dusttImage.getName());
                            FileCopyUtils.copy(dusttImage, newFile);
                            dusttImage.delete();
                        }
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
                            weather.setCumulonimbus(skyCondition.isCumulonimbus());
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

