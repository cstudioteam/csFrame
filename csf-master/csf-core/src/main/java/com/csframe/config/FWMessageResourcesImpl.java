package com.csframe.config;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.csframe.common.FWConstantCode;
import com.csframe.context.FWContext;

@ApplicationScoped
@Named("messageResouces")
public class FWMessageResourcesImpl implements FWMessageResources {

  @Inject
  private FWContext ctx;

  @Override
  public String get(String key) {

    return get(key, ctx.getUser().getLanguage());
  }

  @Override
  public String get(String key, Locale locale) {

    ResourceBundle rb = ResourceBundle.getBundle(ctx.getApplicationId(), locale);
    if (!rb.containsKey(key)) {
      throw new FWMissingResourceException(FWConstantCode.NO_KEY);
    } else {
      return rb.getString(key);
    }
  }

  @Override
  public Set<String> keySet() {

    return keySet(ctx.getUser().getLanguage());
  }

  @Override
  public Set<String> keySet(Locale locale) {

    ResourceBundle rb = ResourceBundle.getBundle(ctx.getApplicationId(), locale);
    return rb.keySet();
  }
}
