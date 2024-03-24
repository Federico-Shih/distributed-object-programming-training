package ar.edu.itba.pod.concurrency.iii.practico;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PromotionSender {
    public static void main(String[] args) {
        List< String > promotions = Arrays.asList("Descuento en Café: ", "Descuento en Refrescos: ", "Descuento en Congelados: ");
        notifyPromotionsCompletable(promotions);
        System.out.println("Se realizaron todas las notificaciones de lapromoción.");
    }
    private static void notifyCustomers(String promotion) {
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Cliente: " + promotion);
        } catch (InterruptedException e) {
            //
        }}
    private static void notifyMarketing(String promotion) {
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Marketing: " + promotion);
        } catch (InterruptedException e) {
            //
        }
    }

    private static void notifyPromotionsParallel(List<String> promotions) {
        promotions.parallelStream().forEach((promotion) -> {
            promotion = promotion + "30%";
            promotion = promotion + " Sólo por hoy";
            notifyCustomers(promotion);
        });
        notifyMarketing("Hoy se publicitó un descuento del 30%");
    }

    private static void notifyPromotionsCompletable(List<String> promotions) {
        CompletableFuture<Void> completed = new CompletableFuture<>();
        completed.thenRunAsync(() -> {
            notifyMarketing("Hoy se publicitó un descuento del 30%");
        });
        promotions.parallelStream().forEach((promotion) -> {
            promotion = promotion + "30%";
            promotion = promotion + " Sólo por hoy";
            notifyCustomers(promotion);
        });
        completed.complete(null);
    }

    private static void notifyPromotions(List<String> promotions) {
        for (String promotion : promotions) {
            notifyCustomers(String.format("%s 30%% Sólo por hoy", promotion));
        }
        notifyMarketing("Hoy se publicitó un descuento del 30%");
    }
}
