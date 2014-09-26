package com.migueljteixeira.clipmobile.network;

import android.content.Context;

import com.migueljteixeira.clipmobile.entities.Student;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class StudentRequest extends Request {

    public static List<Student> signIn(Context context, String username, String password) {

        Elements links = initialRequest(context, username, password)
                .body()
                .select("a[href]");

        List<Student> students = new LinkedList<Student>();

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
                student.setNumberID(student_numberID);
                student.setNumber(student_number);

                System.out.println("numberID: " + student_numberID);
                System.out.println("number: " + student_number);

                students.add(student);
            }
        }

        if (students.size() > 0)
            return students;

        return null;
    }

}
