package com.backend.graafik.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.backend.graafik.model.Shift;
import com.backend.graafik.model.Worker;

public class WorkersList {
    private final List<Worker> workersList = new ArrayList<>();

    public WorkersList() {
        // Liivi
        List<Integer> liiviVacationDays = new ArrayList<>();
        List<Integer> liiviDesiredVacationDays = new ArrayList<>();
        HashMap<Integer, Shift> liiviDesiredWorkDays = new HashMap<>();
        List<Integer> liiviSickDays = new ArrayList<>();
        List<Integer> liiviTrainingDays = new ArrayList<>();
        Worker liivi = new Worker(0, "Liivi", 168, 1.0, 0, 0, liiviVacationDays, liiviDesiredVacationDays,
                liiviDesiredWorkDays, liiviSickDays, liiviTrainingDays);
        workersList.add(liivi);

        // Rita
        List<Integer> ritaVacationDays = new ArrayList<>();
        List<Integer> ritaDesiredVacationDays = new ArrayList<>();
        HashMap<Integer, Shift> ritaDesiredWorkDays = new HashMap<>();
        List<Integer> ritaSickDays = new ArrayList<>();
        List<Integer> ritaTrainingDays = new ArrayList<>();
        Worker rita = new Worker(1, "Rita", 168, 1.0, 0, 0, ritaVacationDays, ritaDesiredVacationDays,
                ritaDesiredWorkDays, ritaSickDays, ritaTrainingDays);
        workersList.add(rita);

        // Ingrid
        List<Integer> ingridVacationDays = new ArrayList<>();
        List<Integer> ingridDesiredVacationDays = new ArrayList<>();
        HashMap<Integer, Shift> ingridDesiredWorkDays = new HashMap<>(Map.of(24, new Shift(24, Shift.PIKK_PÄEV)));
        List<Integer> ingridSickDays = new ArrayList<>();
        List<Integer> ingridTrainingDays = new ArrayList<>();
        Worker ingrid = new Worker(2, "Ingrid", 168, 1.0, 0, 0, ingridVacationDays, ingridDesiredVacationDays,
                ingridDesiredWorkDays, ingridSickDays, ingridTrainingDays);
        workersList.add(ingrid);

        // Monika
        List<Integer> monikaVacationDays = new ArrayList<>();
        List<Integer> monikaDesiredVacationDays = new ArrayList<>(Arrays.asList(12, 13, 14));
        HashMap<Integer, Shift> monikaDesiredWorkDays = new HashMap<>(Map.of(24, new Shift(12, Shift.LÜHIKE_PÄEV)));
        List<Integer> monikaSickDays = new ArrayList<>();
        List<Integer> monikaTrainingDays = new ArrayList<>();
        Worker monika = new Worker(3, "Monika", 168, 1.0, 0, 0, monikaVacationDays, monikaDesiredVacationDays,
                monikaDesiredWorkDays, monikaSickDays, monikaTrainingDays);
        workersList.add(monika);

        // Tiiu
        List<Integer> tiiuVacationDays = new ArrayList<>();
        List<Integer> tiiuDesiredVacationDays = new ArrayList<>(Arrays.asList(7, 21, 22, 23));
        HashMap<Integer, Shift> tiiuDesiredWorkDays = new HashMap<>();
        List<Integer> tiiuSickDays = new ArrayList<>();
        List<Integer> tiiuTrainingDays = new ArrayList<>();
        Worker tiiu = new Worker(4, "Tiiu", 168, 1.0, 0, 0, tiiuVacationDays, tiiuDesiredVacationDays,
                tiiuDesiredWorkDays, tiiuSickDays, tiiuTrainingDays);
        workersList.add(tiiu);

        // Kristel
        List<Integer> kristelVacationDays = new ArrayList<>();
        List<Integer> kristelDesiredVacationDays = new ArrayList<>();
        HashMap<Integer, Shift> kristelDesiredWorkDays = new HashMap<>();
        List<Integer> kristelSickDays = new ArrayList<>();
        List<Integer> kristelTrainingDays = new ArrayList<>();
        Worker kristel = new Worker(5, "Kristel", 168, 1.0, 0, 0, kristelVacationDays, kristelDesiredVacationDays,
                kristelDesiredWorkDays, kristelSickDays, kristelTrainingDays);
        workersList.add(kristel);

        // Kärolin
        List<Integer> kärolinVacationDays = new ArrayList<>();
        List<Integer> kärolinDesiredVacationDays = new ArrayList<>();
        HashMap<Integer, Shift> kärolinDesiredWorkDays = new HashMap<>();
        List<Integer> kärolinSickDays = new ArrayList<>();
        List<Integer> kärolinTrainingDays = new ArrayList<>();
        Worker kärolin = new Worker(6, "Kärolin", 168, 1.0, 0, 0, kärolinVacationDays, kärolinDesiredVacationDays,
                kärolinDesiredWorkDays, kärolinSickDays, kärolinTrainingDays);
        workersList.add(kärolin);

        // Liina
        List<Integer> liinaVacationDays = new ArrayList<>();
        List<Integer> liinaDesiredVacationDays = new ArrayList<>();
        HashMap<Integer, Shift> liinaDesiredWorkDays = new HashMap<>();
        List<Integer> liinaSickDays = new ArrayList<>();
        List<Integer> liinaTrainingDays = new ArrayList<>();
        Worker liina = new Worker(7, "Liina", 168, 1.0, 0, 0, liinaVacationDays, liinaDesiredVacationDays,
                liinaDesiredWorkDays, liinaSickDays, liinaTrainingDays);
        workersList.add(liina);

        // Maarika
        List<Integer> maarikaVacationDays = new ArrayList<>();
        List<Integer> maarikaDesiredVacationDays = new ArrayList<>();
        HashMap<Integer, Shift> maarikaDesiredWorkDays = new HashMap<>();
        List<Integer> maarikaSickDays = new ArrayList<>();
        List<Integer> maarikaTrainingDays = new ArrayList<>();
        Worker maarika = new Worker(8, "Maarika", 168, 1.0, 0, 0, maarikaVacationDays, maarikaDesiredVacationDays,
                maarikaDesiredWorkDays, maarikaSickDays, maarikaTrainingDays);
        workersList.add(maarika);
    }


    // Getter method for the list
    public List<Worker> getWorkersList() {
        return workersList;
    }

}