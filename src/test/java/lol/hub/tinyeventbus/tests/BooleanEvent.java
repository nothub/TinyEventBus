package lol.hub.tinyeventbus.tests;

class BooleanEvent extends Event {

    final boolean value;

    BooleanEvent(boolean value) {
        this.value = value;
    }

}
