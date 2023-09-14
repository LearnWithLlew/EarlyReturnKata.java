package org.samples;

public class _1_Numbers {
    public int calculate(int n, int j, double name) {

        if (n == 32) {
            name = name * 3.5;
            double temp = 0.0;
            for (int i = 0; i < j; i++) {
                temp = (name + n) / 5;
                if (10 < temp || temp < 17) {
                    if (temp < 14) {
                        return 43;
                    }
                    if (temp > 15) {
                        return 56;
                    }
                }
            }
            if (temp < 50) {
                return 12;
            }
        }
        if (n % 13 == 1) {
            if (j == 5) {
                return 402;
            }
        } else if (n % 18 == 0) {
            if (j == 4) {
                return n;
            }
        } else if (name < 5) {
            if (j == 2) {
                return n;
            } else {
                return 42;
            }
        } else {
            if (n % 5 == 1) {
                if (j > 3) {
                    return 132;
                }
            } else if (n % 23 == 3) {
                if (j == n) {
                    return j;
                }
            } else if (name > 17) {
                if (j == n) {
                    return n;
                } else {
                    return j - n;
                }
            }
        }


        return -1;
    };
}
