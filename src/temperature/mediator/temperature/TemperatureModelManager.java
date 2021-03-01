package temperature.mediator.temperature;

import temperature.model.Temperature;
import temperature.model.TemperatureList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TemperatureModelManager implements TemperatureModel
{
  private TemperatureList temperatureList;
  private PropertyChangeSupport property;

  public TemperatureModelManager()
  {
    temperatureList = new TemperatureList();
    property = new PropertyChangeSupport(this);
  }

  @Override public void addTemperature(String id, double value)
  {
    Temperature temperature = new Temperature(id, value);
    Temperature old = getLastInsertedTemperature();
    temperatureList.addTemperature(temperature);
    if (old != null && old.getValue() != temperature.getValue())
    {
      System.out.println("-->" + temperature + " (from: " + old + ")");
    }
    property.firePropertyChange("TemperatureChanged",
        temperatureList.getLastTemperature("2"),
        temperatureList.getLastTemperature("1"));
  }

  @Override public Temperature getLastInsertedTemperature()
  {
    return temperatureList.getLastTemperature(null);
  }

  @Override public Temperature getLastInsertedTemperature(String id)
  {
    System.out.println(temperatureList.getSize());
    return temperatureList.getLastTemperature(id);
  }

  @Override public void addListener(String propertyName,
      PropertyChangeListener listener)
  {
    if (propertyName == null) // all events
    {
      property.addPropertyChangeListener(listener);
    }
    else // a specific event
    {
      property.addPropertyChangeListener(propertyName, listener);
    }
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener)
  {
    if (propertyName == null) // all events
    {
      property.removePropertyChangeListener(listener);
    }
    else // a specific event
    {
      property.removePropertyChangeListener(propertyName, listener);
    }
  }

}