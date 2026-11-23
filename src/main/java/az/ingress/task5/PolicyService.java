package az.ingress.task5;

/**
 * Avto sığorta polisinin yaradılması.
 */
public class PolicyService {

    /*
     * TODO:
     *  1. Polisə iki yeni, istəyə bağlı sahə əlavə et:
     *       durationMonths - müddət, default 12. 12 deyilsə çıxışda ", 6 months" görünsün
     *       email          - default yoxdur. Varsa çıxışda ", email nigar@mail.az" görünsün
     *  2. Polis yaratmaq oxunaqlı olsun: hansı dəyərin hansı sahəyə getdiyi kodda aydın görünsün
     *     (true, false, null ardıcıllıqları olmasın). Yeni sahə əlavə edəndə mövcud kodu dəyişmək lazım olmasın.
     *  3. Məcburi sahələr yoxlanmalıdır. Yoxdursa, polis yaradılmasın və xəta versin:
     *       "policyNo is required", "plate is required", "ownerName is required", "price must be positive"
     *  4. Yaradılmış polis dəyişdirilə bilməz: sahələr final qalsın, setter olmasın.
     */
    static class Policy {
        private final String policyNo;       // məcburi
        private final String plate;          // məcburi
        private final String ownerName;      // məcburi
        private final double price;          // məcburi
        private final String driverName;     // istəyə bağlı
        private final boolean glassCover;    // istəyə bağlı
        private final boolean theftCover;    // istəyə bağlı
        private final boolean roadAssistance; // istəyə bağlı
        private final String discountCode;   // istəyə bağlı

        Policy(String policyNo, String plate, String ownerName, double price) {
            this(policyNo, plate, ownerName, price, null, false, false, false, null);
        }

        Policy(String policyNo, String plate, String ownerName, double price, String driverName) {
            this(policyNo, plate, ownerName, price, driverName, false, false, false, null);
        }

        Policy(String policyNo, String plate, String ownerName, double price, String driverName,
               boolean glassCover, boolean theftCover, boolean roadAssistance, String discountCode) {
            this.policyNo = policyNo;
            this.plate = plate;
            this.ownerName = ownerName;
            this.price = price;
            this.driverName = driverName;
            this.glassCover = glassCover;
            this.theftCover = theftCover;
            this.roadAssistance = roadAssistance;
            this.discountCode = discountCode;
        }

        @Override
        public String toString() {
            return policyNo + ": " + plate + ", " + ownerName + ", " + price + " AZN"
                    + (driverName != null ? ", driver " + driverName : "")
                    + (glassCover ? ", glass" : "")
                    + (theftCover ? ", theft" : "")
                    + (roadAssistance ? ", road assistance" : "")
                    + (discountCode != null ? ", discount " + discountCode : "");
        }
    }

    public static void main(String[] args) {
        Policy basic = new Policy("P-1", "10-AA-111", "Aysel Mammadova", 50);
        Policy withDriver = new Policy("P-2", "90-BB-222", "Murad Aliyev", 50, "Leyla Aliyeva");
        Policy full = new Policy("P-3", "77-CC-333", "Kamran Ismayilov", 50, null, true, false, true, "SPRING10");

        System.out.println(basic);      // P-1: 10-AA-111, Aysel Mammadova, 50.0 AZN
        System.out.println(withDriver); // P-2: 90-BB-222, Murad Aliyev, 50.0 AZN, driver Leyla Aliyeva
        System.out.println(full);       // P-3: 77-CC-333, Kamran Ismayilov, 50.0 AZN, glass, road assistance, discount SPRING10

        // TODO: dəyişikliklərdən sonra yuxarıdakı 3 polisi yeni üsulla yarat (çıxış dəyişməməlidir) və bunları əlavə et:
        //   P-4, 10-DD-444, Nigar Guliyeva, 30 AZN, oğurluq, 6 ay, email nigar@mail.az
        //     -> P-4: 10-DD-444, Nigar Guliyeva, 30.0 AZN, theft, 6 months, email nigar@mail.az
        //   P-5, nömrəsiz (plate yoxdur), Elvin Babayev, 50 AZN
        //     -> Error: plate is required
    }
}
