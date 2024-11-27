package lv.kristianskaneps.autoserviss.engine.extensions;

import io.quarkiverse.renarde.util.I18N;
import io.quarkus.arc.Arc;
import io.quarkus.qute.TemplateExtension;

@TemplateExtension(namespace = "i18n")
public class LocaleExtensions {
    private static I18N getI18n() {
        try (var handle = Arc.container().instance(I18N.class)) {
            return handle.get();
        }
    }

    public static String locale() {
        return getI18n().getLanguage();
    }
}
