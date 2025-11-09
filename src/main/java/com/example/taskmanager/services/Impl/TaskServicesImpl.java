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

import com.example.taskmanager.dto.EditTaskdto;
import com.example.taskmanager.dto.TaskDetails;
import com.example.taskmanager.dto.User;
import com.example.taskmanager.mysql.entities.Task;
import com.example.taskmanager.mysql.repositories.AuthRepository;
import com.example.taskmanager.mysql.repositories.TaskRepository;
import com.example.taskmanager.services.AuthServices;
import com.example.taskmanager.services.TaskServices;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServicesImpl implements TaskServices {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    AuthServices authServices;

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

    @Override
    public ResponseEntity<?> deleteTask(Integer id, String token) {
        // Verify user
        User user = authServices.verifyToken(token);
        if (user == null) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        // Find task
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Task does not exist");
        }

        Task task = taskOpt.get();

        // Check if user owns the task
        if (!Objects.equals(task.getUserid(), user.getUserId())) {
            return ResponseEntity.status(403).body("Access denied");
        }

        // Delete
        taskRepository.deleteById(id);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @Override
    public ResponseEntity<?> editTask(Integer id, EditTaskdto editTaskdto, String token) {
        // Step 1: Verify user
        User user = authServices.verifyToken(token);
        if (user == null) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        // Step 2: Find task
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            return ResponseEntity.status(404).body("Task not found");
        }

        Task task = optionalTask.get();

        // Step 3: Check ownership
        if (!Objects.equals(task.getUserid(), user.getUserId())) {
            return ResponseEntity.status(403).body("Access denied");
        }

        // Step 4: Update fields (only if provided)
        if (editTaskdto.getNewTask() != null) {
            task.setTask(editTaskdto.getNewTask());
        }
        if (editTaskdto.getNewDuedatetime() != null) {
            task.setDuedatetime(editTaskdto.getNewDuedatetime());
        }
        if(editTaskdto.getNewPriority() != null){
            task.setPriority(editTaskdto.getNewPriority());
        }

        // Save updated task
        taskRepository.save(task);

        // Step 5: Return success
        return ResponseEntity.ok(Map.of(
                "message", "Task updated successfully",
                "task", task
        ));
    }
}