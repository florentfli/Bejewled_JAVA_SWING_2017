public class ControlGroup {

    Model model = new Model();
    View view = new View(model);

    ControlButtonGame cbg = new ControlButtonGame(model, view);
    ControlMenu ct = new ControlMenu(model, view);
}
