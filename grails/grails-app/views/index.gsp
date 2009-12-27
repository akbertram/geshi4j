<g:geshi lang="php" linenumbers="true">
if (!function_exists('stripos')) {
    // the offset param of preg_match is not supported below PHP 4.3.3
    if (GESHI_PHP_PRE_433) {
        /**
         * @ignore
         */
        function stripos($haystack, $needle, $offset = null) {
            if (!is_null($offset)) {
                $haystack = substr($haystack, $offset);
            }
            if (preg_match('/'. preg_quote($needle, '/') . '/', $haystack, $match, PREG_OFFSET_CAPTURE)) {
                return $match[0][1];
            }
            return false;
        }
    }
    else {
        /**
         * @ignore
         */
        function stripos($haystack, $needle, $offset = null) {
            if (preg_match('/'. preg_quote($needle, '/') . '/', $haystack, $match, PREG_OFFSET_CAPTURE, $offset)) {
                return $match[0][1];
            }
            return false;
        }
    }
}
</g:geshi>