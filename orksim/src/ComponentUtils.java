package net.sf.openrocket.sitl;

import java.util.Collection;
import java.util.List;

import net.sf.openrocket.util.ArrayList;
import net.sf.openrocket.l10n.Translator;
import net.sf.openrocket.startup.Application;
import net.sf.openrocket.rocketcomponent.Rocket;
import net.sf.openrocket.rocketcomponent.RocketComponent;

public class ComponentUtils{

    private Rocket rocket;
    private static final Translator trans = Application.getTranslator();

    // constructor
    public ComponentUtils() {}

    public ComponentUtils(Rocket rocket) {
      this.rocket = rocket;
    }

    private void recurseAllComponentsDepthFirst(RocketComponent comp, List<RocketComponent> traversalOrder){
  		traversalOrder.add(comp);
  		for( RocketComponent child : comp.getChildren()) {
  			recurseAllComponentsDepthFirst(child, traversalOrder);
  		}
  	}

    //overloaded
    private void recurseAllComponentsDepthFirst(RocketComponent comp, List<RocketComponent> traversalOrder, String componentName) {
      if (comp.getComponentName() == componentName) {
        traversalOrder.add(comp);
      }
      for (RocketComponent child : comp.getChildren()) {
        recurseAllComponentsDepthFirst(child, traversalOrder, componentName);
      }
    }

    private void recursivelyFindComponent(RocketComponent source, RocketComponent destination, String componentName) {
      if (source.getComponentName() == componentName) {
        destination = source;
        return;
      }
      for (RocketComponent sourceChild : source.getChildren()) {
        recursivelyFindComponent(sourceChild, destination, componentName);
      }
    }

    public List<RocketComponent> getParachuteComponents() {
      List<RocketComponent> traversalOrder = new ArrayList<RocketComponent>();
      recurseAllComponentsDepthFirst(this.rocket, traversalOrder, trans.get("Parachute.Parachute"));
      return traversalOrder;
    }

    public List<RocketComponent> getAllComponents() {
      List<RocketComponent> traversalOrder = new ArrayList<RocketComponent>();
  		recurseAllComponentsDepthFirst(this.rocket, traversalOrder);
  		return traversalOrder;
  	}

    public RocketComponent findComponentWithName(String name) {
      RocketComponent result = null;
      List<RocketComponent> allComponents = getAllComponents();
      for (RocketComponent comp : allComponents) {
        if (comp.getName() == name) {
          result = comp;
          return result;
        }
      }
      return null;
    }

}
