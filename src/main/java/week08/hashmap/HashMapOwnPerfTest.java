package week08.hashmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import java.util.stream.Collectors;

public class HashMapOwnPerfTest {

    public static final int MAP_SIZE = 1_000_000;

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> mapCustom = new HashMapOwn<>();
        List<Integer> randomNumbers = new Random().ints(MAP_SIZE).boxed().collect(Collectors.toList());
        measureTime("filling " + map.getClass() + " with integers", () -> fillMapWithIntegers(map,randomNumbers));
        measureTime("filling " + mapCustom.getClass() + " with integers", () -> fillMapWithIntegers(mapCustom,randomNumbers));
        measureTime("retrieving values from  " + map.getClass(), () -> randomNumbers.forEach(map::get));
        measureTime("retrieving values from  " + mapCustom.getClass(), () -> randomNumbers.forEach(mapCustom::get));
        measureTime("removing values from  " + map.getClass(), () -> randomNumbers.forEach(map::remove));
        measureTime("removing values from  " + mapCustom.getClass(), () -> randomNumbers.forEach(mapCustom::remove));
        System.err.println(mapCustom.size()!=map.size()?"size() doesn't work propely":"");
    }

    private static void fillMapWithIntegers(Map<Integer, Integer> map, List<Integer> randomNumbers) {
        randomNumbers.forEach(randomInt -> map.put(randomInt, randomInt));
    }

    public static long measureTime(String header, Runnable task) {
        System.out.println(header);
        long start = System.currentTimeMillis();
        task.run();
        long timeElapsed = System.currentTimeMillis() - start;
        System.out.println("Execution took " + timeElapsed);
        System.out.println("============================================================");
        return timeElapsed;
    }
}
