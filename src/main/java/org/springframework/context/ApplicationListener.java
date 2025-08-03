package org.springframework.context;

import java.util.EventListener;

public interface ApplicationListener <E extends ApplicationEvent> extends EventListener {

    /**
     * 处理应用事件
     * @param event 应用事件
     */
    void onApplicationEvent(E event);

}
