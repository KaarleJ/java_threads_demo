package fi.utu.tech.assignment6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Käytetään tehtässä 1 muokattua GradingTask-oliota
import fi.utu.tech.common.GradingTask;
// Allokointifunktiot implementoidaan TaskAllocator-luokassa
import fi.utu.tech.common.TaskAllocator;

import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;

public class App6 {
    public static void main(String[] args) {

        // Otetaan funktion aloitusaika talteen suoritusajan laskemista varten
        long startTime = System.currentTimeMillis();

        // Generoidaan kasa esimerkkitehtäväpalautuksia
        List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(21, 200, Strategy.UNFAIR);

        // Tulostetaan tiedot esimerkkipalautuksista ennen arviointia
        for (var ug : ungradedSubmissions) {
            System.out.println(ug);
        }

        // Allokoidaan palautukset arviointitehtäviin
        List<GradingTask> gradingTasks = TaskAllocator.allocate(ungradedSubmissions, 6);

        // Syötetään arviointitehtävät executorille execute metodilla.
        ExecutorService executor = Executors.newFixedThreadPool(6);
        for (var gt : gradingTasks) {
            executor.execute(gt);
        }

        // Lopetetaan uusien säikeiden lisääminen ja odotetaan suorituksen loppumista
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.err.println("Who dared to interrupt my sleep?!");
        }

        // Otetaan arvioidut palautukset talteen
        List<Submission> gradedSubmissions = new ArrayList<>();
        for (var gt : gradingTasks) {
            gradedSubmissions.addAll(gt.getGradedSubmissions());
        }

        // Tulostetaan arvioidut palautukset
        System.out.println("------------ CUT HERE ------------");
        for (var gs : gradedSubmissions) {
            System.out.println(gs);
        }

        // Lasketaan funktion suoritusaika
        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis() - startTime);
    }
}
