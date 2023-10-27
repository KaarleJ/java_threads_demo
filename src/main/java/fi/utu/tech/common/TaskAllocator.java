package fi.utu.tech.common;

import java.util.ArrayList;
import java.util.List;

/**
 * You need to modify this file
 */

public class TaskAllocator {

    /**
     * Allocator that creates list of two (2) GradingTask objects with each having
     * half of the given submissions
     * 
     * @param submissions The submissions to be allocated
     * @return The two GradingTask objects in a list, each having half of the
     *         submissions
     */
    public static List<GradingTask> sloppyAllocator(List<Submission> submissions) {
        List<GradingTask> gradingTasks = new ArrayList<>();
        int halfSize = submissions.size() / 2;
        List<Submission> firstHalf = submissions.subList(0, halfSize);
        List<Submission> secondHalf = submissions.subList(halfSize, submissions.size());
        gradingTasks.add(new GradingTask(firstHalf));
        gradingTasks.add(new GradingTask(secondHalf));
        return gradingTasks;
    }

    /**
     * Allocate List of ungraded submissions to tasks
     * 
     * @param submissions List of submissions to be graded
     * @param taskCount   Amount of tasks to be generated out of the given
     *                    submissions
     * @return List of GradingTasks allocated with some amount of submissions
     *         (depends on the implementation)
     */
    public static List<GradingTask> allocate(List<Submission> submissions, int taskCount) {
        List<GradingTask> gradingTasks = new ArrayList<>();
        int taskSize = submissions.size() / taskCount;
        int remainder = submissions.size() % taskCount;
        for (int i = 0; i < taskCount; i++) {
            int start = i * taskSize;
            int end = (i + 1) * taskSize;
            if (i == taskCount - 1) {
                end += remainder;
            }
            gradingTasks.add(new GradingTask(submissions.subList(start, end)));
        }
        return gradingTasks;
    }
}
