@echo off
chcp 65001 > nul
echo TCDD Seat Finder - Parametre Girisi
echo ======================================================================
echo BAZI ONEMLI ISTASYON ID'LERI:
echo - ANKARA GAR - 98
echo - ISTANBUL(SOGUTLUCESME) - 1325
echo - ISTANBUL(BAKIRKOY) - 1328
echo - ESKISEHIR - 93
echo - ISTANBUL(PENDIK) - 48
echo.
echo ONEMLI NOT: Zaman bilgisi formatini YIL-AY-GUNTsaat:dakika:saniye 
echo             seklinde girmelisiniz. (Ornek: 2026-05-06T06:00:00)
echo ======================================================================
echo Lutfen asagidaki bilgileri girin.
echo.

set /p depId="Kalkis Istasyon ID (Orn: 98 - ANKARA GAR): "
set /p arrId="Varis Istasyon ID (Orn: 1328 - ISTANBUL(BAKIRKOY)): "
set /p depTime="Kalkis Zamani (YIL-AY-GUNTsaat:dakika:saniye): "
set /p arrTime="Varis Zamani  (YIL-AY-GUNTsaat:dakika:saniye): "
set /p depName="Kalkis Istasyon Adi (Orn: ANKARA GAR): "
set /p arrName="Varis Istasyon Adi (Orn: ISTANBUL(BAKIRKOY)): "
set /p cabin="Kabin Tipi (Orn: EKONOMI, BUSINESS): "
echo { > service-information.json
echo   "departureStationId": %depId%, >> service-information.json
echo   "arrivalStationId": %arrId%, >> service-information.json
echo   "departureTime": "%depTime%", >> service-information.json
echo   "arrivalTime": "%arrTime%", >> service-information.json
echo   "departureStationName": "%depName%", >> service-information.json
echo   "arrivalStationName": "%arrName%", >> service-information.json
echo   "cabin": "%cabin%" >> service-information.json
echo } >> service-information.json

echo.
echo service-information.json dosyasi olusturuldu. Uygulama baslatiliyor...
java -jar tcdd-seat-finder-0.0.1-SNAPSHOT.jar
pause
