import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.lang.NumberFormatException;
import java.util.NoSuchElementException;
import java.text.SimpleDateFormat;
import java.lang.NullPointerException;
import java.util.Date;
import java.text.ParseException;

class Main {
    public static void main(String[] args) {
        System.out.println("Введите данные в формате: <Фамилия> <Имя> <Отчество> <дата_рождения:дд.мм.гггг> <номер_телефона:79999999999> <пол:m или f>");
        System.out.println("Пример: Иванов Иван Иванович 12.02.1986 79999999999 m");
        Scanner scanner = new Scanner(System.in);
        String surname, name, patronymic, birthday, phoneNumber, gender;
        try {
            surname = scanner.next();
            name = scanner.next();
            patronymic = scanner.next();
            birthday = scanner.next();
            phoneNumber = scanner.next();
            gender = scanner.next();
        } catch (NoSuchElementException e) {
            System.out.println("Слишком мало данных!");
            System.out.print("e.printStackTrace(): ");
            e.printStackTrace();
            return;
        }
        try {
            long phoneNumberLong = Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            System.out.println("Неверный номер телефона!");
            System.out.print("e.printStackTrace(): ");
            e.printStackTrace();
            return;
        }

        if (!gender.equals("m") && !gender.equals("f")) {
            System.out.println("Неправильно указан пол! m = мужской, f = женский");
            return;
        }

        Date date;
        try {
            SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy");
            parser.setLenient(false);
            date = parser.parse(birthday);
            if (date == null) {
                throw new NullPointerException("date is null");
            }
            if (date.getYear() + 1900 > 9999) {
                throw new NumberFormatException("year > 9999");
            }
        } catch (NullPointerException|ParseException|NumberFormatException e) {
            System.out.println("Неправильно введена дата рождения! Формат: dd.MM.YYYY");
            System.out.print("e.printStackTrace(): ");
            e.printStackTrace();
            return;
        }

        System.out.println("Сохраняем в " + surname + ".txt...");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(surname + ".txt", true), "UTF-8"));
            writer.append(surname + " " + name + " " + patronymic + " " + birthday + " " + phoneNumber + " " + gender);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Не удалось записать данные в файл: " + e.getMessage());
            System.out.print("e.printStackTrace(): ");
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) { writer.close(); }
            } catch (IOException e) {
                System.out.println("<не удалось закрыть файл>");
                System.out.print("e.printStackTrace(): ");
                e.printStackTrace();
            }
        }
    }
}
