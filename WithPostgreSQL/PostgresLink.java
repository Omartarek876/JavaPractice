package WithPostgreSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresLink {
    public static void main(String[] args) {
        // بيانات الاتصال
        String url = "jdbc:postgresql://localhost:5432/nwind"; // استبدل postgres باسم قاعدتك
        String user = "postgres";
        String password = "1234";

        // استخدام Try-with-resources لضمان إغلاق الاتصال تلقائياً (Memory Management)
        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            if (conn != null) {
                System.out.println("✅ Connected to the database successfully!");

                // 1. إنشاء Statement (المحرك الذي يرسل الـ SQL)
                Statement stmt = conn.createStatement();

                // 2. كتابة استعلام SQL (نفس الذي تدربنا عليه)
                String sql = "SELECT first_name , employee_id FROM public.employees";

                // 3. تنفيذ الاستعلام واستقبال النتائج في ResultSet
                ResultSet rs = stmt.executeQuery(sql);

                // 4. قراءة البيانات (Row by Row)
                while (rs.next()) {
                    System.out.println("employee name: " + rs.getString("first_name") + " | ID: " + rs.getInt("employee_id"));
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Connection Data Error: " + e.getMessage());
        }
    }
}