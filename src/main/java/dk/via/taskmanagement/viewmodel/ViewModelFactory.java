package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.model.Model;

public class ViewModelFactory {
    private final LoginViewModel loginViewModel;
    private final RegisterViewModel registerViewModel;
    private final CreateWorkspaceViewModel createWorkspaceViewModel;
    private final WorkspaceViewModel workspaceViewModel;

    private final ManageWorkspaceViewModel manageWorkspaceViewModel;

    public ViewModelFactory(Model model) {
        loginViewModel = new LoginViewModel(model);
        registerViewModel = new RegisterViewModel(model);
        createWorkspaceViewModel = new CreateWorkspaceViewModel(model);
        workspaceViewModel = new WorkspaceViewModel(model);
        manageWorkspaceViewModel = new ManageWorkspaceViewModel(model);
    }

    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }

    public RegisterViewModel getRegisterViewModel() {
        return registerViewModel;
    }

    public CreateWorkspaceViewModel getCreateWorkspaceViewModel() {
        return createWorkspaceViewModel;
    }

    public WorkspaceViewModel getWorkspaceViewModel() {
        return workspaceViewModel;
    }

    public ManageWorkspaceViewModel getManageWorkspaceViewModel() {
        return manageWorkspaceViewModel;
    }
}
