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

        List<Shift> allowedDays24 =new ArrayList<>(Arrays.asList(new Shift(24,Shift.PIKK_PÄEV)));
        List<Shift> allowedDays12 =new ArrayList<>(Arrays.asList(new Shift(12,Shift.LÜHIKE_PÄEV)));

        // Rita
        List<Integer> ritaVacationDays = new ArrayList<>();
        List<Integer> ritaDesiredVacationDays = new ArrayList<>();
        HashMap<Integer, Shift> ritaDesiredWorkDays = new HashMap<>();
        List<Integer> ritaSickDays = new ArrayList<>();
        List<Integer> ritaTrainingDays = new ArrayList<>();

        Worker rita = new Worker(0, "Rita", 168, 1.0, 0, 0, ritaVacationDays, ritaDesiredVacationDays,
                ritaDesiredWorkDays, ritaSickDays, ritaTrainingDays, allowedDays24);
        workersList.add(rita);

        // Ingrid
        List<Integer> ingridVacationDays = new ArrayList<>();
        List<Integer> ingridDesiredVacationDays = new ArrayList<>();
        HashMap<Integer, Shift> ingridDesiredWorkDays = new HashMap<>(Map.of(24, new Shift(24, Shift.PIKK_PÄEV)));
        List<Integer> ingridSickDays = new ArrayList<>();
        List<Integer> ingridTrainingDays = new ArrayList<>();
        Worker ingrid = new Worker(1, "Ingrid", 168, 1.0, 0, 0, ingridVacationDays, ingridDesiredVacationDays,
                ingridDesiredWorkDays, ingridSickDays, ingridTrainingDays, allowedDays24);
        workersList.add(ingrid);

        // Monika
        List<Integer> monikaVacationDays = new ArrayList<>();
        List<Integer> monikaDesiredVacationDays = new ArrayList<>(Arrays.asList(12, 13, 14));
        HashMap<Integer, Shift> monikaDesiredWorkDays = new HashMap<>(Map.of(24, new Shift(12, Shift.LÜHIKE_PÄEV)));
        List<Integer> monikaSickDays = new ArrayList<>();
        List<Integer> monikaTrainingDays = new ArrayList<>();
        Worker monika = new Worker(2, "Monika", 168, 1.0, 0, 0, monikaVacationDays, monikaDesiredVacationDays,
                monikaDesiredWorkDays, monikaSickDays, monikaTrainingDays, allowedDays24);
        workersList.add(monika);

        // Tiiu
        List<Integer> tiiuVacationDays = new ArrayList<>();
        List<Integer> tiiuDesiredVacationDays = new ArrayList<>(Arrays.asList(7, 21, 22, 23));
        HashMap<Integer, Shift> tiiuDesiredWorkDays = new HashMap<>();
        List<Integer> tiiuSickDays = new ArrayList<>();
        List<Integer> tiiuTrainingDays = new ArrayList<>();
        Worker tiiu = new Worker(3, "Tiiu", 168, 1.0, 0, 0, tiiuVacationDays, tiiuDesiredVacationDays,
                tiiuDesiredWorkDays, tiiuSickDays, tiiuTrainingDays, allowedDays24);
        workersList.add(tiiu);


        // Liina
        List<Integer> liinaVacationDays = new ArrayList<>();
        List<Integer> liinaDesiredVacationDays = new ArrayList<>();
        HashMap<Integer, Shift> liinaDesiredWorkDays = new HashMap<>();
        List<Integer> liinaSickDays = new ArrayList<>();
        List<Integer> liinaTrainingDays = new ArrayList<>();
        Worker liina = new Worker(4, "Liina", 168, 1.0, 0, 0, liinaVacationDays, liinaDesiredVacationDays,
                liinaDesiredWorkDays, liinaSickDays, liinaTrainingDays, allowedDays12);
        workersList.add(liina);

        // Maarika
        List<Integer> maarikaVacationDays = new ArrayList<>();
        List<Integer> maarikaDesiredVacationDays = new ArrayList<>();
        HashMap<Integer, Shift> maarikaDesiredWorkDays = new HashMap<>();
        List<Integer> maarikaSickDays = new ArrayList<>();
        List<Integer> maarikaTrainingDays = new ArrayList<>();
        Worker maarika = new Worker(5, "Maarika", 168, 1.0, 0, 0, maarikaVacationDays, maarikaDesiredVacationDays,
                maarikaDesiredWorkDays, maarikaSickDays, maarikaTrainingDays, allowedDays12);
        workersList.add(maarika);
    }


    // Getter method for the list
    public List<Worker> getWorkersList() {
        return workersList;
    }

}