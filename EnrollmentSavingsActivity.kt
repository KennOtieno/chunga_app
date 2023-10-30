import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class EnrollmentSavingsActivity : AppCompatActivity() {

    private lateinit var studentsEnrolledTextView: TextView
    private lateinit var totalBalanceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enrollment_savings)

        studentsEnrolledTextView = findViewById(R.id.studentsEnrolledTextView)
        totalBalanceTextView = findViewById(R.id.totalBalanceTextView)

        val studentsRef = FirebaseDatabase.getInstance().getReference("students")
        studentsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var totalStudents = 0
                var totalBalance = 0

                for (studentSnapshot in snapshot.children) {
                    val student = studentSnapshot.getValue(Student::class.java)
                    if (student != null) {
                        totalStudents++
                        totalBalance += student.accountBalance ?: 0
                    }
                }

                studentsEnrolledTextView.text = "Students Enrolled: $totalStudents"
                totalBalanceTextView.text = "Total Account Balance: $totalBalance Ksh"
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error if needed
            }
        })
    }
}