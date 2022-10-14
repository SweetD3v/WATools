package com.dev4life.watools.ui.fragments

import com.dev4life.watools.databinding.FragmentToolsBinding

class ToolsFragment : BaseFragment<FragmentToolsBinding>() {
    override fun getLayout(): FragmentToolsBinding {
        return FragmentToolsBinding.inflate(layoutInflater)
    }

    override fun onBackPressed() {

    }
}