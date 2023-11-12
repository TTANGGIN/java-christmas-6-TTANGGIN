package christmas.util;

import christmas.config.ErrorMessage;
import christmas.config.Menu;

import java.util.EnumSet;

public class MenuUtils {

    private static final EnumSet<Menu> MENU_ITEMS = EnumSet.allOf(Menu.class);

    public static boolean menuExists(String menuName) {
        return MENU_ITEMS.stream()
                .anyMatch(menu -> menu.getName().equalsIgnoreCase(menuName));
    }

    public static Menu getMenuByName(String menuName) {
        return MENU_ITEMS.stream()
                .filter(menu -> menu.getName().equalsIgnoreCase(menuName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_ORDER_INPUT.getMessage()));
    }
}
