package vn.edu.stu.webservice.model;

public class HangHoa {
    private int ma;
    private String ten;
    private Double gia;

    public HangHoa() {
    }

    public HangHoa(int ma, String ten, Double gia) {
        this.ma = ma;
        this.ten = ten;
        this.gia = gia;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    @Override
    public String toString() {
        return "Mã: " + ma +
                "\nTên: " + ten  +
                "\n Giá: " + gia;
    }
}
