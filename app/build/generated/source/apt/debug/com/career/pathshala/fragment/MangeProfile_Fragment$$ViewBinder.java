// Generated code from Butter Knife. Do not modify!
package com.career.pathshala.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MangeProfile_Fragment$$ViewBinder<T extends com.career.pathshala.fragment.MangeProfile_Fragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230762, "field 'TVChangePass' and method 'onViewClicked'");
    target.TVChangePass = finder.castView(view, 2131230762, "field 'TVChangePass'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131230949, "field 'pencil' and method 'onViewClicked'");
    target.pencil = finder.castView(view, 2131230949, "field 'pencil'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131230731, "field 'ETName'");
    target.ETName = finder.castView(view, 2131230731, "field 'ETName'");
    view = finder.findRequiredView(source, 2131230732, "field 'ETPhone'");
    target.ETPhone = finder.castView(view, 2131230732, "field 'ETPhone'");
    view = finder.findRequiredView(source, 2131230730, "field 'ETMail'");
    target.ETMail = finder.castView(view, 2131230730, "field 'ETMail'");
    view = finder.findRequiredView(source, 2131231048, "field 'srollview'");
    target.srollview = finder.castView(view, 2131231048, "field 'srollview'");
    view = finder.findRequiredView(source, 2131230723, "field 'BTUpdate' and method 'onViewClicked'");
    target.BTUpdate = finder.castView(view, 2131230723, "field 'BTUpdate'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131230738, "field 'IVdelete' and method 'onViewClicked'");
    target.IVdelete = finder.castView(view, 2131230738, "field 'IVdelete'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131230737, "field 'IVdeactiveaccount' and method 'onViewClicked'");
    target.IVdeactiveaccount = finder.castView(view, 2131230737, "field 'IVdeactiveaccount'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131230765, "field 'TVcountrycode'");
    target.TVcountrycode = finder.castView(view, 2131230765, "field 'TVcountrycode'");
    view = finder.findRequiredView(source, 2131230721, "field 'BTBank'");
    target.BTBank = finder.castView(view, 2131230721, "field 'BTBank'");
  }

  @Override public void unbind(T target) {
    target.TVChangePass = null;
    target.pencil = null;
    target.ETName = null;
    target.ETPhone = null;
    target.ETMail = null;
    target.srollview = null;
    target.BTUpdate = null;
    target.IVdelete = null;
    target.IVdeactiveaccount = null;
    target.TVcountrycode = null;
    target.BTBank = null;
  }
}
