package org.esfinge.metadata.container.reading;

import static org.apache.commons.beanutils.PropertyUtils.setProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esfinge.metadata.AnnotationReadingException;
import org.esfinge.metadata.annotation.container.ContainerFor;
import org.esfinge.metadata.AnnotationReader;
import org.esfinge.metadata.container.AnnotationReadingProcessor;
import org.esfinge.metadata.container.ContainerTarget;


public class ProcessFieldsReadingProcessor implements AnnotationReadingProcessor {
	
	private Field fieldAnnoted;
	List<Object> lista;
	Set<Object> set;
	Map<Object,Object> map;
	ParameterizedType fieldGenericType;
	
	@Override
	public void initAnnotation(Annotation an, Field field){
		
		fieldAnnoted = field;
		lista = new ArrayList<Object>();
		set = new HashSet<Object>();
		map = new HashMap<Object,Object>();
		fieldGenericType =(ParameterizedType)field.getGenericType();
	}
	
	
	@Override
	public void read(AnnotatedElement elementWithMetadata, Object container, ContainerTarget target) throws AnnotationReadingException {
		try {
			if (target == ContainerTarget.CLASS) {
				Class<?> clazz = (Class<?>) elementWithMetadata;
				for (Type t1 : fieldGenericType.getActualTypeArguments()){
					Class <?> outputClass =(Class<?>)t1;
					if(!outputClass.equals(Field.class)){
					ContainerFor containerFor = (ContainerFor) outputClass.getDeclaredAnnotation(ContainerFor.class);
					if(!containerFor.vaule().equals(ContainerTarget.FIELDS))
					{
						throw new Exception("ContainerFor: " +containerFor.vaule() +" no same of FIELDS");
					}
					
					for(Field f1: clazz.getDeclaredFields())
					{
						AnnotationReader metadataReader = new AnnotationReader();
						Object containerField = outputClass.newInstance();
						containerField = metadataReader.readingAnnotationsTo(f1, outputClass);
						lista.add(containerField);
						set.add(containerField);
						map.put(f1, containerField);
					}
					if(fieldAnnoted.getType().equals(List.class)){
						setProperty(container,fieldAnnoted.getName(),lista);
					}
					else if(fieldAnnoted.getType().equals(Set.class)){
						setProperty(container,fieldAnnoted.getName(),set);
					}
					else if(fieldAnnoted.getType().equals(Map.class)){
						setProperty(container,fieldAnnoted.getName(),map);
					}
				}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new AnnotationReadingException("Cannot read and record the processFields: "+ fieldAnnoted.getName(), e);
		}
		
	}

}
