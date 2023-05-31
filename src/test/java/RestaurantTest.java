import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setUp(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:31:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(true));
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("12:00:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(true));
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("14:00:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(true));
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("16:00:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(true));
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("18:00:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(true));
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("20:00:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(true));
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("21:00:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(true));
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("21:59:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(true));

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("04:00:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(false));
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("08:00:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(false));
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:00:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(false));
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("22:00:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(false));
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("23:00:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(false));
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("01:00:00"));
        assertThat(mockedRestaurant.isRestaurantOpen(),equalTo(false));
    }


    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //<<<<<<<<<<<<<<<<<<<<<<<ITEM SELECTION>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void select_menu_items_should_display_total_cost_of_selected_items() throws itemNotFoundException {
        ArrayList<String> selectedItems = new ArrayList<String>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");

        String totalCost = restaurant.selectMenuItems(selectedItems);
        assertEquals("Your order will cost: ₹388", totalCost);
    }

    @Test
    public void select_menu_items_should_throw_item_not_found_exception_when_any_selected_item_is_unavailable() {
        ArrayList<String> selectedItems = new ArrayList<String>();
        selectedItems.add("Pizza");
        selectedItems.add("Vegetable lasagne");

        assertThrows(itemNotFoundException.class, () -> restaurant.selectMenuItems(selectedItems));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<ITEM SELECTION>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}