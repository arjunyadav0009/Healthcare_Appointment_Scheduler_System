import java.util.*;

class Patient {
    String name;
    String specialtyNeeded;
    int urgency; // Higher = more urgent

    Patient(String name, String specialtyNeeded, int urgency) {
        this.name = name;
        this.specialtyNeeded = specialtyNeeded;
        this.urgency = urgency;
    }
}

class Doctor {
    String name;
    String specialty;
    int slots; // number of available slots
    int startTime; // in hours (24-hour format)

    Doctor(String name, String specialty, int slots, int startTime) {
        this.name = name;
        this.specialty = specialty;
        this.slots = slots;
        this.startTime = startTime;
    }
}

class Appointment {
    String patientName;
    String doctorName;
    String time;

    Appointment(String patientName, String doctorName, String time) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.time = time;
    }
}

public class HealthcareScheduler {

    //time  Convert 24-hour int to readable time like "10:00 AM"
    public static String formatTime(int hour) {
        if (hour == 0) return "12:00 AM";
        if (hour < 12) return hour + ":00 AM";
        if (hour == 12) return "12:00 PM";
        return (hour - 12) + ":00 PM";
    }

    public static void main(String[] args) {
        // Sample doctors (slots = number of patients they can take, startTime = 10 AM)
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Dr. Arjun", "Cardiology", 2, 10));
        doctors.add(new Doctor("Dr. Vishal singh", "Dermatology", 1, 10));
        doctors.add(new Doctor("Dr. Patel", "Neurology", 2, 9));

        // Sample patients name mention
        PriorityQueue<Patient> patients = new PriorityQueue<>(
                (a, b) -> b.urgency - a.urgency // max-heap by urgency
        );
        patients.add(new Patient("Alok kumar", "Cardiology", 5));
        patients.add(new Patient("sujeet yadav", "Dermatology", 2));
        patients.add(new Patient("Nitesh raj", "Cardiology", 3));
        patients.add(new Patient("Amit ", "Neurology", 4));
        patients.add(new Patient("Anmol Sinha", "Cardiology", 1));

        // Appointment
        List<Appointment> appointments = new ArrayList<>();

        while (!patients.isEmpty()) {
            Patient p = patients.poll();
            boolean assigned = false;

            for (Doctor d : doctors) {
                if (d.specialty.equals(p.specialtyNeeded) && d.slots > 0) {
                    String time = formatTime(d.startTime);
                    appointments.add(new Appointment(p.name, d.name, time));

                    d.startTime++; // next slot is +1 hour
                    d.slots--;
                    assigned = true;
                    break;
                }
            }

            if (!assigned) {
                appointments.add(new Appointment(p.name, " ❌Sorry No Doctor Available", "N/A"));
            }
        }

        // Print the appointment schedule
        System.out.println("=== Appointment Schedule ===");
        for (Appointment a : appointments) {
            System.out.println("Patient: " + a.patientName +
                    " -> " + a.doctorName +
                    " at " + a.time);
        }
    }
}
