

class RecursionStuff {
  public static int pow(int val, int pow) {
    if (val < 0 || pow < 0) { throw new RuntimeException(); }
    if (pow == 0) { return 1; }
    if (pow == 1) { return val; }
    return val * pow(val, pow - 1);
  }

  public static int convertBase(int decimal, int base) {
    if (decimal < 0 || base <= 1 || base > 10) { throw new RuntimeException(); }
    if (decimal < base) { return decimal; }
    return convertBase(decimal / base, base) * 10 + decimal % base;
  }

  public static void main(String[] args) {
    System.out.println(convertBase(11, 4));
  }
}
