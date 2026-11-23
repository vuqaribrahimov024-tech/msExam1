package az.ingress.task4;

/**
 * Şəxsi kabinetdə istifadə hesabatı ekranı (internet, dəqiqə, SMS).
 */
public class UsageReportScreen {

    record User(String name, String phone, boolean operator) {
    }

    // Hesabat mənbəyi. Ekran yalnız bu interfeysi tanımalıdır.
    interface UsageReports {
        String getReport(String phone);
    }

    // Uzaq billing sistemi. Qoşulma yavaşdır (~2 saniyə). Başqa komandaya aiddir, dəyişdirməyin.
    static class BillingSystem implements UsageReports {
        static int connections = 0;
        static int requests = 0;

        BillingSystem() {
            connections++;
            System.out.println("   [billing] connecting...");
        }

        public String getReport(String phone) {
            requests++;
            return phone + ": 12.4 GB, 340 min, 25 SMS";
        }
    }

    private final User user;
    private final UsageReports reports;

    UsageReportScreen(User user) {
        this.user = user;
        this.reports = new BillingSystem(); // ekran açılan kimi billing-ə qoşulur
    }

    /*
     * TODO:
     *  1. Billing-ə qoşulma yalnız ilk dəfə hesabat lazım olanda açılsın.
     *     Ekran açılıb hesabata baxılmırsa, qoşulma olmasın.
     *  2. Abunəçi yalnız öz nömrəsinin hesabatını görə bilər, operator hamısını.
     *     İcazəsi olmayan sorğu billing-ə çatmasın, qoşulma da açılmasın -> "ACCESS DENIED"
     *  3. Hər iki qayda BillingSystem-in qarşısında, eyni UsageReports interfeysi ilə yazılsın.
     *     Ekran yalnız UsageReports-u tanısın, showReport()-da icazə yoxlaması qalmasın.
     *     BillingSystem dəyişdirilmir.
     */
    public void showReport(String phone) {
        if (!user.operator() && !user.phone().equals(phone)) {
            System.out.println(user.name() + " -> ACCESS DENIED");
            return;
        }
        System.out.println(user.name() + " -> " + reports.getReport(phone));
    }

    public static void main(String[] args) {
        UsageReportScreen aysel = new UsageReportScreen(new User("Aysel", "050-111-11-11", false));
        aysel.showReport("050-111-11-11");
        aysel.showReport("050-222-22-22");
        aysel.showReport("050-111-11-11");

        UsageReportScreen operator = new UsageReportScreen(new User("Operator", "-", true));
        operator.showReport("050-222-22-22");

        UsageReportScreen murad = new UsageReportScreen(new User("Murad", "050-333-33-33", false));
        // Murad ekranı açdı, amma hesabata baxmadı

        UsageReportScreen leyla = new UsageReportScreen(new User("Leyla", "050-444-44-44", false));
        leyla.showReport("050-111-11-11");

        System.out.println("Billing connections: " + BillingSystem.connections + ", requests: " + BillingSystem.requests);

        /*
         * TODO: indi 4 qoşulma var. Dəyişikliklərdən sonra gözlənilən çıxış:
         *
         *    [billing] connecting...
         * Aysel -> 050-111-11-11: 12.4 GB, 340 min, 25 SMS
         * Aysel -> ACCESS DENIED
         * Aysel -> 050-111-11-11: 12.4 GB, 340 min, 25 SMS
         *    [billing] connecting...
         * Operator -> 050-222-22-22: 12.4 GB, 340 min, 25 SMS
         * Leyla -> ACCESS DENIED
         * Billing connections: 2, requests: 3
         */
    }
}
