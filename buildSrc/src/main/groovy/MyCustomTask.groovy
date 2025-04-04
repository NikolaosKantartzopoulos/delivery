

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class MyCustomTask extends DefaultTask {
    @TaskAction
    void calculateSum() {
        // Custom logic to calculate the sum of two numbers
        int num1 = 5
        int num2 = 7
        int sum = num1 + num2

        // Print the result
        println "Sum: $sum"
    }
}

