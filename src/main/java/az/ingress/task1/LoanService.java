package az.ingress.task1;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Kredit müraciətinin yoxlanması.
 */
public class LoanService {

    record Application(String name, int age, int monthsAtJob, double salary, double amount) {
    }

    private final Set<String> blacklist = Set.of("Rashad Huseynov");
    private final Set<String> sanctions = Set.of("Ivan Petrov"); // hələ istifadə olunmur
    private final Map<String, Integer> scores = Map.of(
            "Aysel Mammadova", 720, "Leyla Hasanova", 640, "Kamran Ismayilov", 580,
            "Nigar Guliyeva", 760, "Sabina Karimova", 750);
    private int paidRequests = 0;

    // Kredit bürosuna sorğu. Hər çağırış 0.50 AZN-dir.
    private int creditScore(String name) {
        paidRequests++;
        return scores.getOrDefault(name, 0);
    }

    /*
     * TODO:
     *  1. Sanksiya yoxlamasını əlavə et -> "REJECTED: sanctions"
     *  2. Yeni yoxlama: iş stajı ən azı 6 ay olmalıdır -> "REJECTED: work experience"
     *  3. creditScore() pullu sorğudur, ən sonda çağırılsın.
     *  4. Yoxlamalar bu sıra ilə getsin:
     *     sanksiya, yaş, iş stajı, qara siyahı, maaş, kredit skoru
     *  5. Kodu elə qur ki, yeni yoxlama əlavə etmək və ya sıranı dəyişmək
     *     mövcud yoxlamalara toxunmasın.
     */
    public String evaluate(Application app) {
        if (app.age() < 18 || app.age() > 65) {
            return "REJECTED: age";
        }
        if (blacklist.contains(app.name())) {
            return "REJECTED: blacklist";
        }
        if (creditScore(app.name()) < 600) {
            return "REJECTED: credit score";
        }
        if (app.amount() > app.salary() * 10) {
            return "REJECTED: income";
        }
        return "APPROVED";
    }

    public static void main(String[] args) {
        LoanService service = new LoanService();

        List<Application> applications = List.of(
                new Application("Aysel Mammadova", 30, 24, 2000, 10000),  // APPROVED
                new Application("Murad Aliyev", 17, 6, 800, 2000),        // REJECTED: age
                new Application("Rashad Huseynov", 40, 48, 3000, 5000),   // REJECTED: blacklist
                new Application("Leyla Hasanova", 35, 36, 1000, 15000),   // REJECTED: income
                new Application("Kamran Ismayilov", 45, 60, 3000, 20000), // REJECTED: credit score
                new Application("Nigar Guliyeva", 28, 12, 4000, 30000)    // APPROVED
        );

        // TODO: dəyişikliklərdən sonra bu müraciətləri siyahıya əlavə et:
        //   Ivan Petrov,     39 yaş, staj 24 ay, maaş 3000, 5000  -> REJECTED: sanctions
        //   Farid Mammadli,  33 yaş, staj 3 ay,  maaş 5000, 10000 -> REJECTED: work experience
        //   Sabina Karimova, 31 yaş, staj 12 ay, maaş 2500, 20000 -> APPROVED
        // Yuxarıdakı 6 müraciətin nəticəsi dəyişməməlidir.
        // Pullu sorğu sayı: bu 6 müraciət üçün 3 olmalıdır (indi 4), hamısı üçün 4.

        for (Application app : applications) {
            System.out.println(app.name() + " -> " + service.evaluate(app));
        }
        System.out.println("Paid credit bureau requests: " + service.paidRequests);
    }
}
