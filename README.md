Tapşırıqları dərsdə keçdiyimiz design pattern-lərdən istifadə edərək həll edin.

## Mündəricat

| # | Tapşırıq | Kod |
|---|---|---|
| 1 | [Kredit müraciətinin yoxlanması](#tapşırıq-1-kredit-müraciətinin-yoxlanması) | [LoanService.java](src/main/java/az/ingress/task1/LoanService.java) |
| 2 | [Çatdırılma qiymətinin hesablanması](#tapşırıq-2-çatdırılma-qiymətinin-hesablanması) | [DeliveryService.java](src/main/java/az/ingress/task2/DeliveryService.java) |
| 3 | [Ödəniş provayderlərinin qoşulması](#tapşırıq-3-ödəniş-provayderlərinin-qoşulması) | [PaymentService.java](src/main/java/az/ingress/task3/PaymentService.java) |
| 4 | [İstifadə hesabatı](#tapşırıq-4-istifadə-hesabatı) | [UsageReportScreen.java](src/main/java/az/ingress/task4/UsageReportScreen.java) |
| 5 | [Sığorta polisinin yaradılması](#tapşırıq-5-sığorta-polisinin-yaradılması) | [PolicyService.java](src/main/java/az/ingress/task5/PolicyService.java) |

## İşə salmaq

- Layihəni IntelliJ IDEA-da Gradle layihəsi kimi açın (Java 17).
- Hər tapşırıq bir sinifdir. Onun `main()` metodunu işə salın və çıxışı TODO-dakı gözlənilən nəticə ilə müqayisə edin.
- Kompilyasiya: `./gradlew build`

---

## Tapşırıq 1. Kredit müraciətinin yoxlanması

Kod: [src/main/java/az/ingress/task1/LoanService.java](src/main/java/az/ingress/task1/LoanService.java)

Bank kredit verməzdən əvvəl hər müraciəti bir neçə yoxlamadan keçirir: müştərinin yaşı, bankın qara siyahısı, maaşa görə məbləğ limiti və kredit bürosundakı skor. Yoxlamalardan biri uğursuz olanda müraciət dərhal rədd edilir. Kredit bürosuna hər sorğu bank üçün pullu xidmətdir.

### Yeni tələblər

1.  Müştəri beynəlxalq sanksiya siyahısına qarşı yoxlanmalıdır.
2.  Yeni qayda: müştərinin indiki iş yerində stajı ən azı 6 ay olmalıdır.
3.  Kredit bürosuna sorğu yalnız digər bütün yoxlamalar uğurla keçdikdən sonra göndərilməlidir.
4.  Risk departamenti yoxlamaları tez-tez əlavə edir və sırasını dəyişir. Bu, mövcud yoxlamalara toxunmadan edilə bilməlidir.

| \#  | Yoxlama           | Şərt                                    |
|-----|-------------------|-----------------------------------------|
| 1   | Sanksiya siyahısı | müştəri siyahıda olmamalı               |
| 2   | Yaş               | 18–65                                   |
| 3   | İş stajı          | ən azı 6 ay                             |
| 4   | Qara siyahı       | müştəri siyahıda olmamalı               |
| 5   | Maaş              | məbləğ maaşın 10 mislindən çox olmamalı |
| 6   | Kredit skoru      | ən azı 600                              |

Mövcud müraciətlər üzrə qərarlar dəyişməməlidir. Texniki detallar və gözlənilən nəticələr kodda TODO şərhlərindədir.

---

## Tapşırıq 2. Çatdırılma qiymətinin hesablanması

Kod: [src/main/java/az/ingress/task2/DeliveryService.java](src/main/java/az/ingress/task2/DeliveryService.java)

Onlayn mağaza sifarişləri üç üsulla çatdırır: kuryer, pick-up nöqtəsi və ekspres. Hər üsulun öz qiyməti, çatdırılma müddəti və müştəriyə göstərilən adı var. Müştəri üsulu səbətdə seçir və seçim sistemə mətn kimi gəlir.

### Yeni tələblər

1.  Mağaza iki yeni çatdırılma üsulu əlavə edir: eyni gün çatdırılma və poçt qutusu (aşağıdakı cədvəl).
2.  Marketinq komandası tez-tez yeni üsullar sınaqdan keçirir. Yeni üsul əlavə etmək mövcud üsulların hesablanmasına toxunmamalıdır.
3.  Naməlum üsul seçiləndə müştəriyə xəta göstərilməlidir.

| Üsul        | Qiymət                                   | Müddət    |
|-------------|------------------------------------------|-----------|
| Eyni gün    | 15 AZN                                   | həmin gün |
| Poçt qutusu | 3 AZN, sifariş 50 AZN və çoxdursa pulsuz | 2 gün     |

Mövcud üsullar üzrə qiymətlər dəyişməməlidir. Texniki detallar və gözlənilən nəticələr kodda TODO şərhlərindədir.

---

## Tapşırıq 3. Ödəniş provayderlərinin qoşulması

Kod: [src/main/java/az/ingress/task3/PaymentService.java](src/main/java/az/ingress/task3/PaymentService.java)

Fintech şirkəti kartla ödənişləri iki xarici provayder vasitəsilə qəbul edir. Hər provayderin öz proqram kitabxanası var: biri məbləği qəpiklə qəbul edir və xəta kodu qaytarır, digəri manatla işləyir və xəta olanda istisna atır. Komanda bütün provayderlər üçün vahid ödəniş formasını artıq təsdiqləyib, amma hələ istifadə etmir.

### Yeni tələblər

1.  Üçüncü provayder qoşulur. O, ödənişi yalnız ABŞ dolları ilə qəbul edir (1 USD = 1.70 AZN).
2.  Ödəniş xidməti bütün provayderlərlə eyni, təsdiqlənmiş forma ilə işləməlidir və onların kitabxanalarının detallarını bilməməlidir.
3.  Növbəti provayderi qoşmaq ödəniş xidmətinin özünü dəyişdirməməlidir.
4.  Provayderlərin kitabxanaları xaricidir, onları dəyişmək mümkün deyil.

Mövcud ödənişlərin nəticəsi dəyişməməlidir. Texniki detallar və gözlənilən nəticələr kodda TODO şərhlərindədir.

---

## Tapşırıq 4. İstifadə hesabatı

Kod: [src/main/java/az/ingress/task4/UsageReportScreen.java](src/main/java/az/ingress/task4/UsageReportScreen.java)

Mobil operatorun şəxsi kabinetində abunəçi öz internet, dəqiqə və SMS istifadəsini görür, operator isə istənilən nömrənin hesabatına baxa bilir. Hesabat uzaq billing sistemindən gəlir. Bu sistemə hər qoşulma təxminən 2 saniyə çəkir. Hazırda kabinet ekranı açılan kimi billing-ə qoşulur, istifadəçi hesabata heç baxmasa belə.

### Yeni tələblər

1.  Billing sisteminə qoşulma yalnız ilk dəfə hesabat lazım olanda açılmalıdır.
2.  Abunəçi yalnız öz nömrəsinin hesabatını görə bilər, operator isə hamısını. İcazəsi olmayan sorğu billing sisteminə ümumiyyətlə çatmamalıdır.
3.  Bu qaydalar billing sisteminin qarşısında olmalıdır. Kabinet ekranı hesabatı əvvəlki kimi alır və bu qaydalardan xəbərsiz qalır. Billing sistemi başqa komandaya aiddir və dəyişdirilə bilməz.

Ekranda görünən nəticələr dəyişməməlidir, yalnız qoşulmaların sayı azalmalıdır. Gözlənilən çıxış kodda TODO şərhində sətir-sətir verilib.

---

## Tapşırıq 5. Sığorta polisinin yaradılması

Kod: [src/main/java/az/ingress/task5/PolicyService.java](src/main/java/az/ingress/task5/PolicyService.java)

Sığorta şirkəti avtomobil polisi satır. Polisdə məcburi məlumatlar var (polis nömrəsi, avtomobilin nömrəsi, sahibi, qiyməti) və müştərinin seçdiyi əlavələr var (əlavə sürücü, şüşə, oğurluq, yol yardımı, endirim kodu). Hər müştəri əlavələrin fərqli kombinasiyasını seçir. Hazırda polis yaradan kodda hansı dəyərin nəyə aid olduğunu anlamaq çətindir.

### Yeni tələblər

1.  Polisə iki yeni, istəyə bağlı məlumat əlavə olunur: müddət (default 12 ay) və müştərinin e-poçtu.
2.  Polis yaradan kod oxunaqlı olmalıdır: hansı dəyərin hansı məlumata aid olduğu aydın görünməlidir. Yeni məlumat əlavə olunanda mövcud polisləri yaradan kod dəyişməməlidir.
3.  Məcburi məlumatlardan biri yoxdursa, polis yaradılmamalı və xəta verilməlidir.
4.  Yaradılmış polis sonradan dəyişdirilə bilməz.

| Məlumat                                                | Növ                |
|--------------------------------------------------------|--------------------|
| Polis nömrəsi, avtomobilin nömrəsi, sahibi, qiyməti    | məcburi            |
| Əlavə sürücü, şüşə, oğurluq, yol yardımı, endirim kodu | istəyə bağlı       |
| Müddət (default 12 ay), e-poçt                         | istəyə bağlı, yeni |

Mövcud polislərin çıxışı dəyişməməlidir. Texniki detallar və gözlənilən nəticələr kodda TODO şərhlərindədir.
