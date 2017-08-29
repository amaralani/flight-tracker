package ir.mfava.modfava.pardazesh.controller;


import ir.mfava.modfava.pardazesh.model.DTO.WeatherDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/map")
public class MapController {

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String showMap(ModelMap map, HttpSession session) {
        /*
        marand : 38.42347, 45.7663
azarshahr : 37.7338, 45.98053
heris :‌ 38.23818, 47.14508



Tehran main :‌ 51.39404,35.62158
varamin :‌  51.65771 , 35.32857
boomehen : 51.84174 , 35.72422
karaj : 51.00677 , 35.80445


Dezfool main :‌ 48.44971 , 32.34284
??? :‌  48.30688 , 32.043
masjed soleiman :‌ 31.9172, 49.27643
ahvaz : 31.32079, 48.68866



Booshehr main : 51.21826 , 28.76766
ali abad : 28.79655, 51.05621
paygah havaee sh ahmadi :‌ 29.09698, 51.03699



Bandar Abas main :‌ 56.40381,27.19601
qeshm :‌ 26.94778, 56.27472
foroodgah hava darya : 27.16181, 56.17035



Zahedan  main :‌ 60.77637 , 294396
         */
        List<WeatherDTO> weatherDTOList = new ArrayList<>();

        // EAST - AZ
        weatherDTOList.add(new WeatherDTO("46.69189",
                "37.9442",
                " آذربایجان شرقی",
                "<ul><li>وضعیت هوا : ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "cloudy",
                5, 8
        ));
        weatherDTOList.add(new WeatherDTO("45.7663",
                "38.42347",
                "مرند",
                "<ul><li>وضعیت هوا : ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "cloudy",
                9, 18
        ));
        weatherDTOList.add(new WeatherDTO("45.98053",
                "37.7338",
                "آذرشهر",
                "<ul><li>وضعیت هوا : ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "cloudy",
                9, 18
        ));
        weatherDTOList.add(new WeatherDTO("47.14508",
                "38.23818",
                "هریس",
                "<ul><li>وضعیت هوا : ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "cloudy",
                9, 18
        ));

        // TEHRAN
        weatherDTOList.add(new WeatherDTO("51.39267",
                "35.75208",
                "تهران",
                "<ul><li>وضعیت هوا : نیمه ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "partlyCloudy",
                5, 8
        ));
        weatherDTOList.add(new WeatherDTO("51.39404",
                "35.62158",
                "تهران",
                "<ul><li>وضعیت هوا : نیمه ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "partlyCloudy",
                9, 18
        ));
        weatherDTOList.add(new WeatherDTO("51.65771",
                "35.32857",
                "ورامین",
                "<ul><li>وضعیت هوا : نیمه ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "partlyCloudy",
                9, 18
        ));
        weatherDTOList.add(new WeatherDTO("51.84174",
                "35.72422",
                "بومهن",
                "<ul><li>وضعیت هوا : نیمه ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "partlyCloudy",
                9, 18
        ));
        weatherDTOList.add(new WeatherDTO("51.00677",
                "35.80445",
                "کرج",
                "<ul><li>وضعیت هوا : نیمه ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "partlyCloudy",
                9, 18
        ));

        // DEZFOOL
        weatherDTOList.add(new WeatherDTO("48.44971",
                "32.34284",
                "دزفول",
                "<ul><li>وضعیت هوا : ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "cloudy",
                5, 8
        ));
        weatherDTOList.add(new WeatherDTO("48.30688",
                "32.043",
                "ایستگاه هواشناسی",
                "<ul><li>وضعیت هوا : ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "cloudy",
                9, 18
        ));
        weatherDTOList.add(new WeatherDTO("49.27643",
                "31.9172",
                "مسجد سلیمان",
                "<ul><li>وضعیت هوا : ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "cloudy",
                9, 18
        ));
        weatherDTOList.add(new WeatherDTO("48.68866",
                "31.32079",
                "اهواز",
                "<ul><li>وضعیت هوا : ابری</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "cloudy",
                9, 18
        ));

        // BOOSHEHR
        weatherDTOList.add(new WeatherDTO("51.21826",
                "28.76766",
                "بوشهر",
                "<ul><li>وضعیت هوا : رعد و برق</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "lightning",
                5, 8
        ));
        weatherDTOList.add(new WeatherDTO("51.05621",
                "28.79655",
                "علی آباد",
                "<ul><li>وضعیت هوا : رعد و برق</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "lightning",
                9, 18
        ));
        weatherDTOList.add(new WeatherDTO("51.03699",
                "29.09698",
                "پایگاه هوایی شهید احمدی",
                "<ul><li>وضعیت هوا : رعد و برق</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "lightning",
                9, 18
        ));


        // BANDAR ABBAS
        weatherDTOList.add(new WeatherDTO("56.40381",
                "27.19601",
                "بندر عباس",
                "<ul><li>وضعیت هوا : آفتابی</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "sunny",
                5, 8
        ));
        weatherDTOList.add(new WeatherDTO("56.27472",
                "26.94778",
                "قشم",
                "<ul><li>وضعیت هوا : آفتابی</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "sunny",
                9, 18
        ));
        weatherDTOList.add(new WeatherDTO("57.07947",
                "27.12759",
                "میناب",
                "<ul><li>وضعیت هوا : آفتابی</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "sunny",
                9, 18
        ));
        weatherDTOList.add(new WeatherDTO("56.17035",
                "27.16181",
                "فرودگاه هوادریا",
                "<ul><li>وضعیت هوا : آفتابی</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "sunny",
                9, 18
        ));

        // ZAHEDAN
        weatherDTOList.add(new WeatherDTO("60.84229",
                "29.32472",
                "زاهدان",
                "<ul><li>وضعیت هوا : آفتابی</li><li>دما : 25 درجه سانتیگراد</li><li>تاریخ آخرین به روز رسانی : 1396/06/08</li></ul>",
                "sunny",
                5, 18
        ));

        map.put("weatherList", weatherDTOList);
        return "map";
    }
}
