package main.firefighters;

import main.api.Firefighter;

import java.util.ArrayList;
import java.util.List;

/**
 * Some utility functions to help de-clutter FireDispatch
 */
public class FireDispatchUtils {
    public static List<Firefighter[]> permute(Firefighter[] firefighters) {
        List<Firefighter[]> result = new ArrayList<>();
        permuteHelper(0, firefighters, result);
        return result;
    }

    private static void permuteHelper(int start, Firefighter[] firefighters, List<Firefighter[]> result) {
        if (start == firefighters.length - 1) {
            result.add(firefighters);
        }

        for(int index = start; index < firefighters.length; index++) {
            swap(firefighters, index, start);
            permuteHelper(start + 1, firefighters, result);
            swap(firefighters, index, start);
        }
    }

    private static void swap(Firefighter[] firefighters, int a, int b) {
        Firefighter temp = firefighters[a];
        firefighters[a] = firefighters[b];
        firefighters[b] = temp;
    }
}