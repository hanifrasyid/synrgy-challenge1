import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FoodOrderingProgram {
    public static void main(String[] args) {
        ArrayList<String[]> daftarPesanan = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        while (true) {
            String[][] daftarMenu = menuUtama();
            System.out.print("\n=> ");
            int pilihan = input.nextInt();

            if (pilihan == 0) {
                System.out.println("Terima Kasih Sudah Berkunjung di BinarFud!");
                break;
            }

            if (pilihan > 0 && pilihan <= daftarMenu.length) {
                String[] menuItem = daftarMenu[pilihan - 1];
                int qty = qtyPesananCust(menuItem[0], input);

                if (qty == 0) {
                    continue;
                }

                String[] pesanan = {menuItem[0], String.valueOf(qty), menuItem[1]};
                daftarPesanan.add(pesanan);
            }

            if (pilihan == 99) {
                int pilihanCust = konfirmasiPesananCust(daftarPesanan, input);

                if (pilihanCust == 1) {
                    buatStrukBayar(daftarPesanan);
                    break;
                }

                if (pilihanCust == 2) {
                    continue;
                }

                if (pilihanCust == 0) {
                    System.out.println("\nTerima Kasih Sudah Berkunjung di BinarFud!");
                    break;
                }
            }
        }
        input.close();
    }

    public static String[][] menuUtama() {
        bersihkanTerminal();
        String[][] daftarMenu = {
                {"Nasi Goreng   ", "15000"},
                {"Mie Goreng    ", "13000"},
                {"Nasi + Ayam   ", "18000"},
                {"Es Teh Manis  ", "3000"},
                {"Es Jeruk      ", "5000"},
        };

        System.out.println("==========================");
        System.out.println("Selamat datang di BinarFud");
        System.out.println("==========================");
        System.out.println();

        System.out.println("Silahkan Pilih Makanan : ");
        int index = 1;
        int i = 0;
        while (i < daftarMenu.length) {
            System.out.println((index++) + ". " + daftarMenu[i][0] + " | " + daftarMenu[i][1]);
            i++;
        }
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar Aplikasi");

        return daftarMenu;
    }

    public static void bersihkanTerminal() {
        int barisYangDibersihkan = 25;
        int i = 0;

        while (i < barisYangDibersihkan) {
            System.out.println();
            i++;
        }
    }

    public static int konfirmasiPesananCust(ArrayList<String[]> daftarPesanan, Scanner input) {
        bersihkanTerminal();
        System.out.println("========================");
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("========================");
        System.out.println();
        tabelPesananCust(daftarPesanan);
        System.out.println();
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke Menu Utama");
        System.out.println("0. Keluar Aplikasi");
        System.out.print("=> ");
        return input.nextInt();
    }

    public static int qtyPesananCust(String pilihanCust, Scanner input) {
        bersihkanTerminal();
        System.out.println("====================");
        System.out.println("Berapa Pesanan Anda");
        System.out.println("====================");
        System.out.println();
        System.out.println(pilihanCust);
        System.out.println("(Input 0 Untuk Kembali)");
        System.out.print("qty => ");
        return input.nextInt();
    }

    public static void printBaris(int[] columnWidths) {
        for (int width : columnWidths) {
            for (int i = 0; i < width; i++) {
                System.out.print("-");
            }
            System.out.print("+");
        }
        System.out.println();
    }

    public static void tabelPesananCust(ArrayList<String[]> listPesanan) {
        String[] header = {"Nama Makanan", "Qty", "Total"};
        int[] columnWidths = {15, 5, 7};
        int total_qty = 0;
        int total_pesanan = 0;

        int i = 0;
        int pesananSize = listPesanan.size();

        while (i < header.length) {
            System.out.printf("%-" + columnWidths[i] + "s", header[i]);
            i++;
        }
        System.out.println();
        printBaris(columnWidths);

        i = 0;
        while (i < pesananSize) {
            String[] pesan = listPesanan.get(i);
            int qty = Integer.parseInt(pesan[1]);
            int price = Integer.parseInt(pesan[2]);
            int total = qty * price;
            total_qty += qty;
            total_pesanan += total;

            int j = 0;
            int pesanLength = pesan.length;

            while (j < pesanLength) {
                if (j != 2) {
                    System.out.printf("%-" + columnWidths[j] + "s", pesan[j]);
                } else {
                    String totalpesanan = String.valueOf(total);
                    System.out.printf("%-" + columnWidths[j] + "s", totalpesanan);
                }
                j++;
            }
            System.out.println();
            i++;
        }

        printBaris(columnWidths);

        System.out.printf("%-" + columnWidths[0] + "s%-" + columnWidths[1] + "s%-" + columnWidths[2] + "s\n", "Total", total_qty, total_pesanan);
    }

    public static void simpanTabel(BufferedWriter writer, ArrayList<String[]> daftarPesanan) throws IOException {
        writer.write("Nama Makanan       Qty   Total\n");
        writer.write("--------------------------------\n");

        int index = 0;
        while (index < daftarPesanan.size()) {
            String[] pesan = daftarPesanan.get(index);
            String namaMakanan = pesan[0];
            int qty = Integer.parseInt(pesan[1]);
            int harga = Integer.parseInt(pesan[2]);
            int total = qty * harga;
            writer.write(String.format("%-17s %4d %5d\n", namaMakanan, qty, total));
            index++;
        }

        writer.write("--------------------------------\n");
    }

    public static void buatStrukBayar(ArrayList<String[]> daftarPesanan) {
        bersihkanTerminal();
        System.out.println("===============================");
        System.out.println("Struk Pembayaran BinarFud");
        System.out.println("===============================");
        System.out.println();

        System.out.println("Terima Kasih Sudah Memesan di BinarFud");
        System.out.println();
        System.out.println("Di Bawah Ini Adalah Pesanan Anda:");
        System.out.println();
        tabelPesananCust(daftarPesanan);
        System.out.println();
        System.out.println("Pembayaran: BinarCash");
        System.out.println();
        System.out.println("==================================");
        System.out.println("Simpan Struk Ini Sebagai Bukti Pembayaran");
        System.out.println("==================================");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Struk Pembayaran.txt"))) {
            writer.write("===============================\n");
            writer.write("Struk Pembayaran BinarFud\n");
            writer.write("===============================\n\n");
            writer.write("Terima Kasih Sudah Memesan di BinarFud\n\n");
            writer.write("Di Bawah Ini Adalah Pesanan Anda\n\n");
            simpanTabel(writer, daftarPesanan);
            writer.write("Pembayaran: BinarCash\n\n");
            writer.write("==================================\n");
            writer.write("Simpan Struk Ini Sebagai Bukti Pembayaran\n");
            writer.write("==================================\n");
        } catch (IOException e) {
            System.out.println("Gagal Mencetak Pesanan ke File Struk Pembayaran.txt.");
        }
    }
}