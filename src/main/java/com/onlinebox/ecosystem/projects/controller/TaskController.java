package com.onlinebox.ecosystem.projects.controller;

import com.onlinebox.ecosystem.employees.bean.UserManagerBean;
import com.onlinebox.ecosystem.employees.entity.User;
import com.onlinebox.ecosystem.projects.bean.TaskManagerBean;
import com.onlinebox.ecosystem.projects.bean.TaskTypeManagerBean;
import com.onlinebox.ecosystem.projects.entity.Task;
import com.onlinebox.ecosystem.util.DateHelper;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author cedric
 */
@ManagedBean
@ViewScoped
public class TaskController implements Serializable {

    public final static int PERIOD_DAY = 1;
    public final static int PERIOD_WEEK = 2;
    public final static int PERIOD_MONTH = 3;
    @ManagedProperty(value = "#{userSessionController.user}")
    private User user;
    @EJB
    private TaskManagerBean taskBean;
    @EJB
    private TaskTypeManagerBean taskTypeBean;
    @EJB
    private UserManagerBean userBean;
    private List<Task> tasks;
    private Task task;
    private User selectedUser;
    private int periodToDisplay;    //contains the period to display (day, week or month)
    private Date selectedDate;  //contains the current selected date for the date navigation

    /**
     * Creates a new instance of TaskController
     */
    public TaskController() {
        task = new Task();
        task.setTaskDate(new Date());
        periodToDisplay = PERIOD_WEEK;

        selectedDate = new Date();
    }

    /**
     * Initialization method that is called at the creation of the managed bean. This method loads all the tasks of the users.
     */
    @PostConstruct
    void init() {
        selectedUser = user;
        Date[] datePeriod = computePeriodDate();    //Sets the correct value in startDate and endDate: current week
        tasks = taskBean.getByUserAndByPeriod(selectedUser, datePeriod[0], datePeriod[1]);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * This method is called by the JSF Page dashboard_view.xhtml to create a new task or to edit an exising task.
     */
    public void saveTask() {
        System.out.println("saveTask()");
        RequestContext context = RequestContext.getCurrentInstance();
        String message = "";
        boolean isOk = false;
        if (task != null) {
            try {
                if (task.getId() > 0) {
                    //Modify an existing task because id already contains a valid value (>0)
                    updateTask();
                } else {
                    //New task because id does not contain a valid value (<=0)
                    createTask();
                }
            } catch (Exception ex) {
                Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
                message = "Unable to create the task.";
            }


        }
        if (!message.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            isOk = true;
            this.resetTask();
        }
        context.addCallbackParam("isOk", isOk);

    }

    /*
     * Private method that creates a new task. It is called by the method saveTask().
     */
    private void createTask() throws Exception {
        task.setUser(selectedUser);
        task.setTaskType(taskTypeBean.get(task.getTaskType().getId()));
        task = this.taskBean.create(task);
        tasks.add(task);
    }

    /*
     * Private method that modifies an existing task. It is called by the method saveTask().
     */
    private void updateTask() throws Exception {
        task.setTaskType(taskTypeBean.get(task.getTaskType().getId()));
        task = this.taskBean.update(task);
    }

    /**
     * Reset the current selected task, so that when a popup is opened, there is now old data displayed.
     */
    public void resetTask() {
        System.out.println("resetTask()");
        task = new Task();
        task.setTaskDate(new Date());
    }

    /**
     * This method is called by the JSF Page timetracking_view.xhtml to delete an existing task.
     */
    public void deleteTask() {
        System.out.println("deleteTask");
        if (task != null && task.getId() > 0) {
            taskBean.delete(task);
            tasks.remove(task);
        }
    }

    public List<User> getAllUsers() {
        return userBean.getAll();
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    /**
     * This method handles the users filter.
     */
    public void filterByUser() {
        tasks = taskBean.getByUser(selectedUser);
    }

    /**
     * Getter that returns the choosen period (day, week, or month)
     * @return 
     */
    public int getPeriodToDisplay() {
        return periodToDisplay;
    }

    /**
     * Setter that allow to set the modification of the period when user chosse day, week or month
     * @param periodToDisplay 
     */
    public void setPeriodToDisplay(int periodToDisplay) {
        this.periodToDisplay = periodToDisplay;
    }

    public void onPeriodToDisplayChange() {
        Date[] datePeriod = computePeriodDate();
        tasks = taskBean.getByUserAndByPeriod(selectedUser, datePeriod[0], datePeriod[1]);
    }

    /**
     * This method handles the navigation in previous period of time.
     */
    public void onPrevPeriod() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        switch (periodToDisplay) {
            case PERIOD_DAY:
                calendar.add(Calendar.DATE, -1);
                selectedDate = calendar.getTime();
                break;

            case PERIOD_WEEK:
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                selectedDate = calendar.getTime();
                break;

            case PERIOD_MONTH:
                calendar.add(Calendar.MONTH, -1);
                selectedDate = calendar.getTime();
                break;
        }

        Date[] datePeriod = computePeriodDate();
        tasks = taskBean.getByUserAndByPeriod(selectedUser, datePeriod[0], datePeriod[1]);
    }

    /**
     * This method handles the navigation in next period of time.
     */
    public void onNextPeriod() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        switch (periodToDisplay) {
            case PERIOD_DAY:
                calendar.add(Calendar.DATE, 1);
                selectedDate = calendar.getTime();
                break;

            case PERIOD_WEEK:
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                selectedDate = calendar.getTime();
                break;

            case PERIOD_MONTH:
                calendar.add(Calendar.MONTH, 1);
                selectedDate = calendar.getTime();
                break;
        }

        Date[] datePeriod = computePeriodDate();
        tasks = taskBean.getByUserAndByPeriod(selectedUser, datePeriod[0], datePeriod[1]);
    }

    /**
     * This method sets the date to today with the current period. For exampe, if the period is month, then the current month will be selected; if the period is
     * week, then the current week will be selected.
     */
    public void onToday() {

        selectedDate = new Date();
        Date[] datePeriod = computePeriodDate();
        tasks = taskBean.getByUserAndByPeriod(selectedUser, datePeriod[0], datePeriod[1]);
    }

    /**
     * This method returns an array of 2 Date that corresponds to the period of the tasks to display. Position 0 contains the start Date and position 1 contains
     * the end Date. These intervals are computed basing on the attribute selectedDate.
     *
     * @return
     */
    private Date[] computePeriodDate() {

        Date[] datePeriod = new Date[2];

        switch (periodToDisplay) {
            case PERIOD_DAY:
                datePeriod[0] = DateHelper.getMinHour(selectedDate);
                datePeriod[1] = DateHelper.getMaxHour(selectedDate);
                break;

            case PERIOD_WEEK:              
                datePeriod[0] = DateHelper.getFirstDayOfWeek(selectedDate);
                datePeriod[1] = DateHelper.getLastDayOfWeek(selectedDate);         
                break;

            case PERIOD_MONTH:
                datePeriod[0] = DateHelper.getFirstDayOfMonth(selectedDate);
                datePeriod[1] = DateHelper.getLastDayOfMonth(selectedDate);
                break;
        }

        return datePeriod;
    }
    
    /**
     * This method returns the chosen date interval in a understanble format.
     * @return 
     */
    public String getNavigationPeriod() {
        String dateToString = "";
        Date[] datePeriod = computePeriodDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);

        switch (periodToDisplay) {
            case PERIOD_DAY:
                dateToString = new SimpleDateFormat("EEEE").format(selectedDate) + ", " + calendar.get(Calendar.DATE) + " " + new SimpleDateFormat("MMMM").format(selectedDate) + " " + calendar.get(Calendar.YEAR);
                break;

            case PERIOD_WEEK:
                Calendar calStart = Calendar.getInstance();
                calStart.setTime(datePeriod[0]); //Set start of week

                Calendar calEnd = Calendar.getInstance();
                calEnd.setTime(datePeriod[1]); //Set end of week

                if (calStart.get(Calendar.YEAR) != calEnd.get(Calendar.YEAR)) {
                    dateToString = calStart.get(Calendar.DATE) + " " + new SimpleDateFormat("MMMM").format(datePeriod[0]) + " " + calStart.get(Calendar.YEAR) + " - " + calEnd.get(Calendar.DATE) + " " + new SimpleDateFormat("MMMM").format(datePeriod[1]) + " " + calEnd.get(Calendar.YEAR);
                } else {
                    if (calStart.get(Calendar.MONTH) != calEnd.get(Calendar.MONTH)) {
                        dateToString = calStart.get(Calendar.DATE) + " " + new SimpleDateFormat("MMMM").format(datePeriod[0]) + " - " + calEnd.get(Calendar.DATE) + " " + new SimpleDateFormat("MMMM").format(datePeriod[1]) + " " + calEnd.get(Calendar.YEAR);
                    } else {
                        dateToString = calStart.get(Calendar.DATE) + " - " + calEnd.get(Calendar.DATE) + " " + new SimpleDateFormat("MMMM").format(datePeriod[1]) + " " + calEnd.get(Calendar.YEAR);

                    }
                }

                break;

            case PERIOD_MONTH:
                dateToString = new SimpleDateFormat("MMMM").format(selectedDate) + " " + calendar.get(Calendar.YEAR);
                break;
        }
        return dateToString;
    }
}
