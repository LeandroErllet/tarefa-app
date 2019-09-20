package dev.leandro.viveiros.esig.tarefa.view;

import lombok.val;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

import javax.faces.context.FacesContext;
import java.util.Map;


public class ViewScope implements Scope {

    private static final String VIEW_SCOPE_CALLBACKS = "viewScope.callbacks";

    @NotNull
    @Override
    public synchronized Object get(@NotNull String name, @NotNull ObjectFactory<?> objectFactory) {
        var instance = getViewMap().get(name);
        if (instance == null) {
            instance = objectFactory.getObject();
            getViewMap().put(name, instance);
        }
        return instance;
    }

    @Override
    public Object remove(@NotNull String name) {
        var instance = getViewMap().remove(name);
        if (instance == null) {
            val callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
            if (callbacks != null)
                callbacks.remove(name);
        }
        return instance;
    }

    @Override
    public void registerDestructionCallback(@NotNull String name, @NotNull Runnable runnable) {
        val callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
        if (callbacks != null)
            callbacks.put(name, runnable);
    }

    @Override
    public Object resolveContextualObject(@NotNull String key) {
        val facesContext = FacesContext.getCurrentInstance();
        val facesResquestAttributes = new FacesRequestAttributes(facesContext);
        return facesResquestAttributes.resolveReference(key);
    }

    @Override
    public String getConversationId() {
        val facesContext = FacesContext.getCurrentInstance();
        val facesResquestAttributes = new FacesRequestAttributes(facesContext);
        return facesResquestAttributes.getSessionId() + "-" + facesContext.getViewRoot().getViewId();
    }

    private Map<String, Object> getViewMap() {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap();
    }

}