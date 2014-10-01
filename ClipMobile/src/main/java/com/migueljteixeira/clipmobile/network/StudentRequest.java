package com.migueljteixeira.clipmobile.network;

import android.content.Context;

import com.migueljteixeira.clipmobile.entities.Student;
import com.migueljteixeira.clipmobile.entities.StudentYear;
import com.migueljteixeira.clipmobile.entities.User;
import com.migueljteixeira.clipmobile.exceptions.ServerUnavailableException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StudentRequest extends Request {

    private static final String GET_STUDENTS_NUMBERS = "https://clip.unl.pt/utente/eu";
    private static final String GET_STUDENTS_YEARS = "https://clip.unl.pt/utente/eu/aluno?aluno=";

    public static User signIn(Context context, String username, String password)
            throws ServerUnavailableException {

        Elements links = initialRequest(context, username, password)
                .body()
                .select("a[href]");

        User user = new User();

        for (Element link : links) {
            String linkHref = link.attr("href");

            if (linkHref.matches("/utente/eu/aluno[?][_a-zA-Z0-9=&.]*aluno=[0-9]*")) {

                // Remove all the garbage
                String[] numbers = linkHref.split("&");
                numbers = numbers[numbers.length - 1].split("=");

                // Get student number ID and student number
                String student_numberID = numbers[1];
                String student_number = link.text();

                Student student = new Student();
                student.setNumberId(student_numberID);
                student.setNumber(student_number);

                System.out.println("numberID: " + student_numberID);
                System.out.println("number: " + student_number);

                user.addStudent(student);
            }
        }

        return user;
    }

    public static User getStudentsNumbers(Context mContext)
            throws ServerUnavailableException {
        String url = GET_STUDENTS_NUMBERS;

        Elements links = request(mContext, url)
                .body()
                .select("a[href]");

        User user = new User();

        for (Element link : links) {
            String linkHref = link.attr("href");

            if (linkHref.matches("/utente/eu/aluno[?][_a-zA-Z0-9=&.]*aluno=[0-9]*")) {

                // Remove all the garbage
                String[] numbers = linkHref.split("&");
                numbers = numbers[numbers.length - 1].split("=");

                // Get student number ID and student number
                String student_numberID = numbers[1];
                String student_number = link.text();

                Student student = new Student();
                student.setNumberId(student_numberID);
                student.setNumber(student_number);

                System.out.println("numberID: " + student_numberID);
                System.out.println("number: " + student_number);

                user.addStudent(student);
            }
        }

        return user;
    }


    public static Student getStudentsYears(Context mContext, String studentNumberId)
            throws ServerUnavailableException {
        String url = GET_STUDENTS_YEARS + studentNumberId;

        Elements links = request(mContext, url)
                .body()
                .select("a[href]");

        Student student = new Student();
        System.out.println("years!!!!");

        for(Element link : links) {
            String linkHref = link.attr("href");

            if(linkHref.matches("/utente/eu/aluno/ano_lectivo[?][_a-zA-Z0-9=;&.%]*ano_lectivo=[0-9]*")) {
                String year = link.text();

                StudentYear studentYear = new StudentYear();
                studentYear.setYear(year);

                System.out.println("year: " + year);

                student.addYear(studentYear);
            }
        }

        return student;
    }

}
