package az.ingress.task3;

import java.math.BigDecimal;
import java.util.List;

/**
 * Kartla ödənişlərin provayderlərə göndərilməsi.
 */
public class PaymentService {

    // Komandanın təsdiqlədiyi standart interfeys. Hələ istifadə olunmur. Dəyişdirməyin.
    interface PaymentGateway {
        boolean pay(String card, double amountAzn);
    }

    // ---------- Provayderlərin SDK-ları: xarici kitabxanalardır, dəyişdirmək olmaz ----------

    static class PayLinkSdk {
        /** Məbləğ qəpiklə. @return 0 uğurlu, başqa kod xətadır. */
        int charge(String card, long amount) {
            System.out.println("   PayLink: " + amount + " qapik");
            return amount <= 100_000 ? 0 : 51;
        }
    }

    static class EasyPaySdk {
        /** Uğursuz olanda exception atır. */
        void makePayment(BigDecimal amount, String currency, String card) {
            System.out.println("   EasyPay: " + amount + " " + currency);
            if (amount.compareTo(new BigDecimal("2000")) > 0) {
                throw new IllegalStateException("Limit exceeded");
            }
        }
    }

    static class GlobalPaySdk {
        /** Yalnız USD qəbul edir. @return "OK" və ya "DECLINED". */
        String process(double amountUsd, String card) {
            System.out.println("   GlobalPay: " + String.format("%.2f", amountUsd) + " USD");
            return amountUsd <= 1000 ? "OK" : "DECLINED";
        }
    }

    // ---------- Servis ----------

    private final PayLinkSdk payLink = new PayLinkSdk();
    private final EasyPaySdk easyPay = new EasyPaySdk();

    /*
     * TODO:
     *  1. Yeni provayder GLOBALPAY qoş (GlobalPaySdk). O yalnız USD qəbul edir: 1 USD = 1.70 AZN.
     *  2. Hər provayder PaymentGateway interfeysi ilə işləsin. Bu metod SDK-ları birbaşa tanımasın.
     *  3. Yeni provayder əlavə edəndə bu metodu dəyişmək lazım olmasın.
     *  4. SDK siniflərini və PaymentGateway interfeysini dəyişmək olmaz.
     */
    public boolean pay(String provider, String card, double amountAzn) {
        if (provider.equals("PAYLINK")) {
            long qapik = Math.round(amountAzn * 100);
            int code = payLink.charge(card, qapik);
            return code == 0;
        } else if (provider.equals("EASYPAY")) {
            try {
                easyPay.makePayment(BigDecimal.valueOf(amountAzn), "AZN", card);
                return true;
            } catch (IllegalStateException e) {
                return false;
            }
        }
        throw new IllegalArgumentException("Unknown provider: " + provider);
    }

    record Payment(String provider, String card, double amountAzn) {
    }

    public static void main(String[] args) {
        PaymentService service = new PaymentService();

        List<Payment> payments = List.of(
                new Payment("PAYLINK", "4169-1111", 150),  // SUCCESS
                new Payment("PAYLINK", "4169-1111", 1500), // FAILED
                new Payment("EASYPAY", "5522-2222", 300),  // SUCCESS
                new Payment("EASYPAY", "5522-2222", 2500)  // FAILED
        );

        // TODO: GLOBALPAY hazır olanda bu ödənişləri siyahıya əlavə et:
        //   GLOBALPAY, 4098-3333, 340 AZN  -> GlobalPay: 200.00 USD, SUCCESS
        //   GLOBALPAY, 4098-3333, 1870 AZN -> GlobalPay: 1100.00 USD, FAILED
        // Yuxarıdakı 4 ödənişin nəticəsi dəyişməməlidir.

        for (Payment p : payments) {
            boolean ok = service.pay(p.provider(), p.card(), p.amountAzn());
            System.out.println(p.provider() + " " + p.amountAzn() + " AZN -> " + (ok ? "SUCCESS" : "FAILED"));
        }
    }
}
