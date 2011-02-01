package net.vivin.validation;

import net.vivin.validation.service.ClassConstraintInformation;
import net.vivin.validation.service.ValidationConstraintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

public class ClientSideValidationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    ValidationConstraintsService validationConstraintsService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView != null) {
            Map<String, Object> modelMap = modelAndView.getModel();

            if(modelMap != null) {
                Set<Map.Entry<String, Object>> modelMapEntrySet = modelMap.entrySet();
                Map.Entry[] modelMapEntryArray = modelMapEntrySet.toArray(new Map.Entry[modelMapEntrySet.size()]);
        
                boolean found = false;
                int i = 0;

                while(i < modelMapEntrySet.size() && !found) {
                    Map.Entry entry = modelMapEntryArray[i];
                    Object value = entry.getValue();

                    if(hasValidateClientSideAnnotation(value)) {
                        found = true;
                        ClassConstraintInformation classConstraintsInformation = validationConstraintsService.getValidationConstraints(value.getClass());
                        modelAndView.getModelMap().addAttribute("propertyToConstraintInstancesMap", classConstraintsInformation.getPropertyToConstraintInstancesMap());
                        modelAndView.getModelMap().addAttribute("compoundConstraintDefinitionSet", classConstraintsInformation.getCompoundConstraintDefinitionSet());
                    }

                    i++;
                }
            }
        }
    }

    private boolean hasValidateClientSideAnnotation(Object object) {
        boolean found = false;
        int i = 0;

        if (object != null) {
            Annotation[] annotations = object.getClass().getDeclaredAnnotations();
            while(i < annotations.length && !found) {
                Annotation annotation = annotations[i];
                found = annotation.annotationType().equals(ValidateClientSide.class);
                i++;
            }
        }

        return found;
    }
}
