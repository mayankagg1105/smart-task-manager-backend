//package com.example.taskmanager.services.Impl;
//
//import com.example.taskmanager.dto.TaskDetails;
//import com.example.taskmanager.dto.User;
//import com.example.taskmanager.mysql.entities.Task;
//import com.example.taskmanager.mysql.repositories.TaskRepository;
//import com.example.taskmanager.services.TaskServices;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class TaskServicesImpl implements TaskServices {
//
//    @Autowired
//    TaskRepository taskRepository;
//
//    public void createTask(TaskDetails taskDetails, User user) {
//        Task task = new Task();
//        task.setTask(taskDetails.getTask());
//        String priority = getPriorityFromAi(taskDetails.getTask());
//        task.setPriority(priority);
//        task.setDuedatetime(taskDetails.getDuedatetime());
//        task.setUserid(user.getUserId());
//        taskRepository.save(task);
//    }
//
//    public List<Task> getTasks(User user){
//       return taskRepository.findByUserId(user.getUserId());
//    }
//
//    public String getPriorityFromAi(String task){
//        //api call function which call the google gemini key
//        return "sample";
//    }
//}


package com.example.taskmanager.services.Impl;

import com.example.taskmanager.dto.TaskDetails;
import com.example.taskmanager.dto.User;
import com.example.taskmanager.mysql.entities.Task;
import com.example.taskmanager.mysql.repositories.TaskRepository;
import com.example.taskmanager.services.TaskServices;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServicesImpl implements TaskServices {

    @Autowired
    TaskRepository taskRepository;

    private final Client geminiClient;
    private final String MODEL_NAME = "gemini-2.5-flash"; // Use a fast model for quick classification

    // Inject the API key from application.properties
    public TaskServicesImpl(@Value("${gemini.api.key}") String apiKey) {
        // Initialize the Gemini Client with the API key
        this.geminiClient = Client.builder().apiKey(apiKey).build();
    }

    // Existing createTask function updated to use getPriorityFromAi
    @Override
    public void createTask(TaskDetails taskDetails, User user) {
        Task task = new Task();
        task.setTask(taskDetails.getTask());

        // *Call AI function here*
        String priority = getPriorityFromAi(taskDetails.getTask());
        task.setPriority(priority); // Set priority from AI

        task.setDuedatetime(taskDetails.getDuedatetime());
        task.setUserid(user.getUserId());
        taskRepository.save(task);
    }

    @Override
    public List<Task> getTasks(User user){
        return taskRepository.findByUserId(user.getUserId());
    }

    /**
     * Calls the Gemini API to determine the priority of a task.
     * @param task The task description.
     * @return The determined priority (LOW, MEDIUM, or HIGH).
     */
    public String getPriorityFromAi(String task) {
        String defaultPriority = "MEDIUM";

        // Define the prompt with the required output constraint
        String prompt = String.format(
                "Analyze the following task description and return only one word for its priority. " +
                        "The allowed options are: LOW, MEDIUM, HIGH. " +
                        "Task: '%s'", task
        );

        try {
            GenerateContentResponse response = geminiClient.models.generateContent(
                    MODEL_NAME,
                    prompt,
                    null // Use default configuration
            );
            System.out.println("ai response"+ response);
            // Extract the generated text, sanitize it, and convert to uppercase.
            String generatedText = response.text().trim().toUpperCase();

            // Simple validation to ensure the response is one of the allowed options
            if (generatedText.equals("LOW") || generatedText.equals("MEDIUM") || generatedText.equals("HIGH")) {
                return generatedText;
            } else {
                System.err.println("AI returned an invalid priority: " + generatedText + ". Falling back to default.");
                return defaultPriority;
            }
        } catch (Exception e) {
            System.err.println("Error calling Gemini API for priority: " + e.getMessage());
            // Return a default priority in case of an API error
            return defaultPriority;
        }
    }
}