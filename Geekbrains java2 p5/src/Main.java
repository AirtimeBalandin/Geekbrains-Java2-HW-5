import java.util.Arrays;

public class Main {
    static final int size = 10000;
    static final int half = size / 2;

    public static void main(String[] args) {
        doPartOne(createArray());
        doPartTwo(createArray());
    }

    private static float[] createArray(){
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        return arr;
    }

    public static void doPartOne(float[] arr) {
        System.out.println(Arrays.toString(arr));
        long a = System.currentTimeMillis();
        for (int i = 0; i <arr.length ; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(Arrays.toString(arr));
        System.out.println("Time for part one: "+ (System.currentTimeMillis() - a));
    }

    public static void doPartTwo(float[] arr) {
        System.out.println(Arrays.toString(arr));
        float[] arrOne = new float[half];
        float[] arrTwo = new float[half];
        long b = System.currentTimeMillis();
        System.arraycopy(arr, 0, arrOne, 0, half);
        System.arraycopy(arr, half, arrTwo, 0, half);
        System.out.println(Arrays.toString(arrOne));
        System.out.println(Arrays.toString(arrTwo));
        Thread calcOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <arrOne.length ; i++) {
                    arrOne[i] = (float)(arrOne[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        calcOne.start();

        Thread calcTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <arrTwo.length ; i++) {
                    arrTwo[i] = (float) (arrTwo[i] * Math.sin(0.2f + (i+half) / 5) * Math.cos(0.2f + (i+half) / 5) * Math.cos(0.4f + (i+half) / 2));
                }
            }
        });
        calcTwo.start();

        try{
            calcOne.join();
            calcTwo.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.arraycopy(arrOne, 0, arr, 0, half);
        System.arraycopy(arrTwo, 0, arr, half, half);
        System.out.println(Arrays.toString(arr));
        System.out.println("The total time for part two: " + (System.currentTimeMillis() - b));
    }
}
