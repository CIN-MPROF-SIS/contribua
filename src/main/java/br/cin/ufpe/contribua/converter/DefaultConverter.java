package br.cin.ufpe.contribua.converter;

import br.cin.ufpe.contribua.model.AbstractModel;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

//@FacesConverter(value = "classeConverter")    
@FacesConverter(forClass = AbstractModel.class)
public class DefaultConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        
        if (value != null && !value.isEmpty()) {
            return (AbstractModel) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof AbstractModel) {
            AbstractModel entity= (AbstractModel) value;
            if (entity != null && entity instanceof AbstractModel && entity.getId() != null) {
                uiComponent.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
    }
}