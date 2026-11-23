package az.ingress.task2;

import java.util.List;

/**
 * Sifarişin çatdırılma qiymətinin hesablanması.
 */
public class DeliveryService {

    record Order(String customer, double total, double weightKg, String deliveryType) {
    }

    /*
     * TODO:
     *  1. Yeni çatdırılma növü SAME_DAY: 15 AZN, 0 gün, ad "Same day".
     *  2. Yeni çatdırılma növü LOCKER: 3 AZN, sifariş 50 AZN və çoxdursa pulsuz, 2 gün, ad "Parcel locker".
     *  3. Hər növün qiyməti, müddəti və adı bir yerdə olsun. Yeni növ əlavə edəndə
     *     price(), days(), label() metodlarını dəyişmək lazım olmasın.
     *  4. Növ API-dən String kimi gəlir. String-ə görə növü seçmək bir yerdə olsun.
     *     Naməlum növ -> "Unknown delivery type: X"
     */
    public double price(Order order) {
        switch (order.deliveryType()) {
            case "COURIER":
                if (order.total() >= 100) {
                    return 0;
                }
                return 5 + order.weightKg() * 0.5;
            case "PICKUP":
                return 2;
            case "EXPRESS":
                return 10 + order.weightKg();
            default:
                throw new IllegalArgumentException("Unknown delivery type: " + order.deliveryType());
        }
    }

    public int days(Order order) {
        switch (order.deliveryType()) {
            case "COURIER":
                return 2;
            case "PICKUP":
                return 3;
            case "EXPRESS":
                return 1;
            default:
                throw new IllegalArgumentException("Unknown delivery type: " + order.deliveryType());
        }
    }

    public String label(Order order) {
        switch (order.deliveryType()) {
            case "COURIER":
                return "Courier";
            case "PICKUP":
                return "Pickup point";
            case "EXPRESS":
                return "Express";
            default:
                throw new IllegalArgumentException("Unknown delivery type: " + order.deliveryType());
        }
    }

    public String receipt(Order order) {
        return String.format("%s: %s, %.2f AZN, %d day(s)",
                order.customer(), label(order), price(order), days(order));
    }

    public static void main(String[] args) {
        DeliveryService service = new DeliveryService();

        List<Order> orders = List.of(
                new Order("Aysel", 120, 3, "COURIER"),  // Courier, 0.00 AZN, 2 day(s)
                new Order("Murad", 40, 2, "COURIER"),   // Courier, 6.00 AZN, 2 day(s)
                new Order("Leyla", 30, 1, "PICKUP"),    // Pickup point, 2.00 AZN, 3 day(s)
                new Order("Kamran", 80, 4, "EXPRESS")   // Express, 14.00 AZN, 1 day(s)
        );

        // TODO: yeni növlər hazır olanda bu sifarişləri siyahıya əlavə et:
        //   Nigar,  60, 1 kq, SAME_DAY -> Same day, 15.00 AZN, 0 day(s)
        //   Rashad, 70, 2 kq, LOCKER   -> Parcel locker, 0.00 AZN, 2 day(s)
        //   Elvin,  20, 1 kq, LOCKER   -> Parcel locker, 3.00 AZN, 2 day(s)
        //   Farid,  50, 1 kq, DRONE    -> Unknown delivery type: DRONE
        // Yuxarıdakı 4 sifarişin nəticəsi dəyişməməlidir.

        for (Order order : orders) {
            try {
                System.out.println(service.receipt(order));
            } catch (IllegalArgumentException e) {
                System.out.println(order.customer() + ": " + e.getMessage());
            }
        }
    }
}
