# TCDD Seat Finder 🚄

TCDD Seat Finder, Türkiye Cumhuriyeti Devlet Demiryolları (TCDD) sistemindeki Yüksek Hızlı Tren (YHT) ve diğer tren seferlerini otomatik olarak tarayan ve boş koltuk bulunduğunda size anında e-posta bildirimi gönderen bir otomasyon aracıdır.

Bot, TCDD'nin Nginx tabanlı Güvenlik Duvarını (WAF) atlatmak için modern tarayıcı taklidi (Browser Spoofing) özellikleriyle donatılmıştır.

## 🚀 Özellikler
- **Otomatik Bilet Tarama:** Belirlediğiniz tarih, güzergah ve kabin tipine (Ekonomi/Business) göre sürekli bilet arar.
- **E-Posta Bildirimi:** Boş yer bulunduğu anda sistem anında belirlediğiniz e-posta adresine detaylı bir bildirim atar.
- **Güvenli Konfigürasyon:** Şifreleriniz ve özel token'larınız kodun içine gömülmez, dışarıdan okunur.
- **Kullanıcı Dostu Arayüz:** Terminal ekranından kalkış, varış ve tarih parametrelerini çok kolay bir şekilde girebilirsiniz.

---

## 🛠️ Kurulum

### Gereksinimler
- **Java 21** veya üzeri bir sürüm bilgisayarınızda yüklü olmalıdır.
- (Opsiyonel) Kaynak kodunu derlemek isterseniz Maven. *(Derlenmiş `.jar` dosyası `run/` klasöründe hazır gelmektedir).*

### 1. Projeyi Klonlayın
```bash
git clone https://github.com/kullanici-adiniz/tcdd-seat-finder.git
cd tcdd-seat-finder
```

### 2. Ayarları Yapılandırın (Çok Önemli!)
Projenin çalışabilmesi için TCDD bağlantı bilgilerini ve e-posta şifrelerinizi girmeniz gerekir. 
Güvenliğiniz için bu dosyalar `.gitignore` ile korunmaktadır.

1. `run` klasörünün içine girin.
2. `application.properties.example` dosyasının bir kopyasını oluşturup adını **`application.properties`** yapın.
3. Dosyayı bir metin editörüyle açın ve aşağıdaki kısımları kendi bilgilerinize göre doldurun:

```ini
# E-posta bildirimlerini gönderecek olan Gmail adresiniz:
spring.mail.username=gonderen_email@gmail.com

# Gmail hesabınıza ait 16 haneli "Uygulama Şifresi" (App Password)
spring.mail.password=xxxx xxxx xxxx xxxx

# Bildirimin kime (size) gönderileceği:
tcdd.mail.from=gonderen_email@gmail.com
tcdd.mail.to=alici_email@gmail.com

# TCDD API Token'ınız (Aşağıda nasıl bulunacağı anlatılmıştır)
tcdd.api.token=eyJhb...
```

#### 💡 TCDD Token Nasıl Bulunur?
TCDD API token'ları güvenlik gereği belirli aralıklarla değişir. Güncel token'ınızı bulmak için:
1. Tarayıcınızdan `ebilet.tcddtasimacilik.gov.tr` adresine gidin.
2. `F12` tuşuna basarak Geliştirici Araçlarını açın ve **Network (Ağ)** sekmesine geçin.
3. Sitede rastgele bir bilet araması yapın (Örn: Ankara -> Eskişehir).
4. Ağ sekmesine düşen `train-availability` isteğine tıklayın.
5. `Headers` altındaki `Request Headers` (İstek Başlıkları) bölümünde yer alan **`Authorization`** değerini kopyalayın.
6. Kopyaladığınız değeri `application.properties` içindeki `tcdd.api.token=` kısmına yapıştırın.

---

## 🏃‍♂️ Kullanım

Tüm ayarları tamamladıktan sonra, uygulamayı başlatmak çok kolaydır:

1. `run` klasörü içindeki **`run.bat`** (Windows) dosyasına çift tıklayın.
2. Açılan terminal ekranında sizden bazı bilgiler istenecektir. İstenen **İstasyon ID** numaralarını ve **Zaman Bilgilerini** girin:
   - İstasyon ID'leri ekranın üstündeki tabloda yer almaktadır (Örn: Ankara için 98).
   - Zaman formatını mutlaka `YIL-AY-GUNTsaat:dakika:saniye` şeklinde girin (Örn: `2026-05-06T06:00:00`).
3. Tüm bilgileri girdikten sonra bot arka planda TCDD sunucularını taramaya başlayacaktır.

---

## 🔒 Güvenlik Notu
`run/application.properties` ve oluşturulan json dosyaları `.gitignore` dosyasında tanımlıdır. Bu sayede kişisel şifreleriniz ve özel token'larınız **hiçbir zaman GitHub'a yüklenmez**. Açık kaynak dünyasına gönül rahatlığıyla katkıda bulunabilirsiniz.
