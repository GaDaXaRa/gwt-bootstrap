package com.github.gwtbootstrap.datepicker.client.ui.base;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.base.HasVisibility;
import com.github.gwtbootstrap.client.ui.base.HasVisibleHandlers;
import com.github.gwtbootstrap.client.ui.event.*;
import com.github.gwtbootstrap.datepicker.client.ui.util.LocaleUtil;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

import java.util.Date;

/**
 * Base DatePicker component.
 *
 * @author Carlos Alexandro Becker
 * @since 2.0.3.0
 */
public class DateBoxBase extends Widget implements HasValue<Date>, HasValueChangeHandlers<Date>, HasVisibility,
        HasVisibleHandlers, HasAllDatePickerHandlers {

    private final TextBox box;
    private String format;
    private String language;
    private DateTimeFormat dtf;

    public DateBoxBase() {
        this.box = new TextBox();
        setElement(box.getElement());
        setFormat("mm/dd/yyyy");
        this.language = LocaleUtil.getLanguage();
        setValue(new Date());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFormat(String format) {
        this.format = format;
        Date oldValue = getValue();
        this.dtf = DateTimeFormat.getFormat(format.replaceAll("mm", "MM"));
        if (oldValue != null) {
            setValue(oldValue);
        }
    }

    public void setLanguage(String language) {
        this.language = language;
        LocaleUtil.forceLocale(language);
    }

    /**
     * Returns the internal instance of textbox element. Use only if know what you are doing.
     *
     * @return internal textbox intance.
     */
    protected TextBox getBox() {
        return box;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getValue() {
        return dtf != null ? dtf.parse(box.getValue()) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Date value) {
        setValue(value, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Date value, boolean fireEvents) {
        box.setValue(dtf.format(value));
        if (fireEvents) {
            ValueChangeEvent.fire(this, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> dateValueChangeHandler) {
        return addHandler(dateValueChangeHandler, ValueChangeEvent.getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onLoad() {
        super.onLoad();
        configure();
    }

    /**
     * Configure the elements for a specific widget.
     * Use only if you know what you are doing.
     *
     * @param w: the widget to configure.
     */
    protected void configure(Widget w) {
        w.getElement().setAttribute("data-date-format", format);
        w.getElement().setAttribute("data-date-language", language);
        configure(w.getElement());
    }

    /**
     * dateChange event handler.
     */
    public void onChange() {
        ValueChangeEvent.fire(this, getValue());
    }

    /**
     * configure this datepicker.
     */
    protected void configure() {
        configure(this);
    }

    /**
     * call jquery datepicker plugin in a element.
     *
     * @param e: Element that will be transformed in a datepicker.
     */
    protected native void configure(Element e) /*-{
        var that = this;
        $wnd.jQuery(e).datepicker();
//        $wnd.jQuery(e).on('changeDate', function () {
//            that.@com.github.gwtbootstrap.datepicker.client.ui.base.DateBoxBase::onChange()();
//        });
//        $wnd.jQuery(e).on("show", function () {
//            that.@com.github.gwtbootstrap.datepicker.client.ui.base.DateBoxBase::show()();
//        });
//        $wnd.jQuery(e).on("hide", function () {
//            that.@com.github.gwtbootstrap.datepicker.client.ui.base.DateBoxBase::hide()();
//        });
    }-*/;

    private native void execute(Element e, String cmd) /*-{
        $wnd.jQuery(e).datepicker(cmd);
    }-*/;

    private void execute(String cmd) {
        execute(getElement(), cmd);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        execute("show");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        execute("hide");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggle() {
        // TODO toggle.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HandlerRegistration addHideHandler(HideHandler handler) {
        return addHandler(handler, HideEvent.getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HandlerRegistration addHiddenHandler(HiddenHandler handler) {
        return addHandler(handler, HiddenEvent.getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HandlerRegistration addShowHandler(ShowHandler handler) {
        return addHandler(handler, ShowEvent.getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HandlerRegistration addShownHandler(ShownHandler handler) {
        return addHandler(handler, ShownEvent.getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWeekStart(int start) {
        getElement().setAttribute("data-date-weekstart", start + "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStartDate(String startDate) {
        getElement().setAttribute("data-date-startdate", startDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStartDate_(Date startDate) {
        setStartDate(dtf.format(startDate));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setEndDate(String endDate) {
        getElement().setAttribute("data-date-enddate", endDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEndDate_(Date endDate) {
        setEndDate(dtf.format(endDate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAutoClose(boolean autoclose) {
        getElement().setAttribute("data-date-autoclose", autoclose + "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStartView(ViewMode mode) {
        setStartView(mode.name());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStartView(String mode) {
        getElement().setAttribute("data-date-startview", mode.toLowerCase());
    }
}
