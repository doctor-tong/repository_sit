package com.example.alienshooter;

import java.util.HashMap;
import java.util.Map;

public class InputManager {
    private  Map<String, ButtonState> button_state_map = new HashMap<>();
     {
        button_state_map.put("BUTTON_LEFT", ButtonState.Wait);
        button_state_map.put("BUTTON_RIGHT", ButtonState.Wait);
        button_state_map.put("BUTTON_JUMP", ButtonState.Wait);
        button_state_map.put("BUTTON_SHOOT", ButtonState.Wait);
    }

    public enum ButtonState {
        Pressed,
        Touched,
        Up,
        Wait,
        FORBID
    }

    public Enum<ButtonState> getButtonState (String key) {
        return button_state_map.get(key);
    }

    public void setButtonWait(String key)
    {
        if (key != null) {
            button_state_map.put(key, ButtonState.Wait);
        }
    }

    public void setButtonPressed(String key) {
        if (key != null&&(button_state_map.get(key)==ButtonState.Wait||button_state_map.get(key)==ButtonState.Up)) {
            button_state_map.put(key, ButtonState.Pressed);
        }
    }

    public void setButtonTouched(String key) {
        if (key != null&&button_state_map.get(key)==ButtonState.Pressed) {
            button_state_map.put(key, ButtonState.Touched);
        }
    }
    public void setButtonUp(String key) {
        if (key != null) {
            if (button_state_map.get(key)==ButtonState.Pressed||button_state_map.get(key)==ButtonState.Touched)
                button_state_map.put(key, ButtonState.Up);
        }
    }
    public void setButtonFORBID(String key) {
        if (key != null) {
                button_state_map.put(key, ButtonState.FORBID);
        }
    }

}

