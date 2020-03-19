function arrayString(array) {
    if (array.length == 0) return "[]";
    var buf = "[";
    var i = 0;
    while (true) {
        buf += array[i];
        i++;
        if (i == array.length)
            return buf + "]";
        buf += ", ";
    }
}