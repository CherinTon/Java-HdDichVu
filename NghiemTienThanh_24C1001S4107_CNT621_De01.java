import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Lớp CUDAN
class CUDAN implements Serializable {
    private static final long serialVersionUID = 1L; 
    private int soPhong;
    private String tenCD;
    private String soDT;

    public CUDAN() {}

    public CUDAN(int soPhong, String tenCD, String soDT) {
        this.soPhong = soPhong;
        this.tenCD = tenCD;
        this.soDT = soDT;
    }

    public void nhap(Scanner scanner) {
        System.out.print("Nhap so phong:  ");
        soPhong = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Nhap ten cu dan:  ");
        tenCD = scanner.nextLine();
        System.out.print("Nhap SDT:  ");
        soDT = scanner.nextLine();
    }

    public void xuat() {
        System.out.println("So phong:  " + soPhong);
        System.out.println("Ten cu dan:  " + tenCD);
        System.out.println("SDT:  " + soDT);
    }
}

// Lớp HOADONDICHVU kế thừa từ lớp CUDAN
class HOADONDICHVU extends CUDAN implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tenDV;
    private int soLuongSuDung;
    private double donGia;

    public HOADONDICHVU() {}

    public HOADONDICHVU(int soPhong, String tenCD, String soDT, String tenDV, int soLuongSuDung, double donGia) {
        super(soPhong, tenCD, soDT);
        this.tenDV = tenDV;
        this.soLuongSuDung = soLuongSuDung;
        this.donGia = donGia;
    }

    public void nhap(Scanner scanner) {
        super.nhap(scanner);
        System.out.print("Nhap ten dich vu:  ");
        tenDV = scanner.nextLine();
        System.out.print("Nhap so luong su dung:  ");
        soLuongSuDung = scanner.nextInt();
        System.out.print("Nhap don gia:  ");
        donGia = scanner.nextDouble();
    }

    public void xuat() {
        super.xuat();
        System.out.println("Ten dich vu: " + tenDV);
        System.out.println("So luong su dung: " + soLuongSuDung);
        System.out.println("Don gia: " + donGia);
        System.out.println("Thanh tien:  " + thanhTien());
    }

    public double thanhTien() {
        return soLuongSuDung * donGia;
    }
}

public class NghiemTienThanh_24C1001S4107_CNT621_De01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<HOADONDICHVU> danhSachHoaDon = new ArrayList<>();
        int choice;
        String fileName = "hoadondichvu.dat";

        do {
            System.out.println("\nVui long chon:");
            System.out.println("1. Tao file moi");
            System.out.println("2. Doc file da luu");
            System.out.println("3. Xoa file da luu");
            System.out.println("0. Thoat chuong trinh");
            System.out.print("Nhap lua chon: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Khởi tạo lại danh sách hóa đơn trước khi nhập dữ liệu
                    danhSachHoaDon = new ArrayList<>();
                    // Nhập danh sách hóa đơn dịch vụ từ bàn phím
                    System.out.print("\nNhap so luong HD dich vu: ");
                    int soLuongHoaDon = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    for (int i = 0; i < soLuongHoaDon; i++) {
                        System.out.println("\nNhap thong tin HD thu " + (i + 1) + ":");
                        HOADONDICHVU hoaDon = new HOADONDICHVU();
                        hoaDon.nhap(scanner);
                        danhSachHoaDon.add(hoaDon);
                    }

                    // In lại danh sách hóa đơn dịch vụ đã nhập
                    System.out.println("\nDanh sach HD dich vu da nhap: ");
                    for (HOADONDICHVU hoaDon : danhSachHoaDon) {
                        hoaDon.xuat();
                        System.out.println();
                    }

                    // Tính tổng tiền tất cả các hóa đơn dịch vụ đã nhập
                    double tongTien = 0;
                    for (HOADONDICHVU hoaDon : danhSachHoaDon) {
                        tongTien += hoaDon.thanhTien();
                    }
                    System.out.println("Tong tien cac HD dich vu: " + tongTien);

                    // Lưu danh sách hóa đơn dịch vụ vào file "hoadondichvu.dat"
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                        oos.writeObject(danhSachHoaDon);
                        System.out.print("\tSave file as 'hoadondichvu.dat' successfully! ");
                        System.out.println("File path: " + new File(fileName).getAbsolutePath());
                    } catch (IOException e) {
                        System.out.print("\tError saving file: " + e.getMessage());
                        System.out.println("\tCause: " + (e.getCause() != null ? e.getCause() : "Unknown cause"));
                    }
                    break;

                case 2:
                    // Đọc danh sách hóa đơn từ file
                    danhSachHoaDon = docDanhSach(fileName);
                    // Kiểm tra và in danh sách nếu đọc thành công
                    if (danhSachHoaDon != null) {
                        System.out.println("\nDanh sach hoa don dich vu da luu file:");
                        for (HOADONDICHVU hoaDon : danhSachHoaDon) {
                            hoaDon.xuat();
                            System.out.println();
                        }
                    } else {
                        System.out.println("File not found or read error!");
                    }
                    break;
                    

                case 3:
                    // Xóa file đã lưu
                    File file = new File(fileName);
                    if (file.exists()) {
                        if (file.delete()) {
                            System.out.println("\nFile '" + fileName + "' da duoc xoa thanh cong.");
                        } else {
                            System.out.println("\nXoa file that bai. Vui long thu lai.");
                        }
                    } else {
                        System.out.println("\nKhong tim thay file de xoa.");
                    }
                    break;

                case 0:
                    System.out.println("Thoat chuong trinh.");
                    break;

                default:
                    System.out.println("Lua chon khong hop le! Vui long chon lai.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    public static ArrayList<HOADONDICHVU> docDanhSach(String filename) {
        ArrayList<HOADONDICHVU> danhSachHoaDon = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {
                danhSachHoaDon = (ArrayList<HOADONDICHVU>) obj;
                System.out.println("\nFile '" + filename + "' was read successfully.");
            } else {
                System.out.println("\nDu lieu khong phai la DS HD dich vu.");
            }
        } catch (IOException e) {
            System.out.println("\nError reading file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("\nClass not found error when reading file.");
        }
        return danhSachHoaDon;
    }
}
